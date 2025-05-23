import {useEffect, useState} from "react";
import axios from "axios";
import {CloudUpload, Delete} from '@mui/icons-material';
import Button from '@mui/material/Button';
import {XRayImage} from "../types/XrayImage.tsx";


const XRayDocuments: React.FC = () => {
    const [images, setImages] = useState<XRayImage[]>([]);
    const [selectedFile, setSelectedFile] = useState<File | null>(null);

    useEffect(() => {
        axios.get<XRayImage[]>('/api/xray')
            .then((res) => {
                setImages(res.data);
            })
            .catch((err) => console.error("Error loading XRay documents:", err));
    }, []);

    const handleUpload = async () => {
        if (!selectedFile) return;

        const formData = new FormData();
        formData.append("file", selectedFile);

        try {
            const res = await axios.post("/api/xray", formData, {
                headers: {"Content-Type": "multipart/form-data"},
            });

            setImages([...images, res.data]);
            setSelectedFile(null);
        } catch (error) {
            console.error("Error while uploading:", error);
        }
    };

    const handleDelete = async (id: string) => {
        try {
            await axios.delete(`/api/xray/${id}`);
            setImages(images.filter((image) => image.id !== id));
        } catch (error) {
            console.error("Error while deleting:", error);
        }
    };

    return (
        <div className="p-4">
            <h2 className="text-xl font-bold mb-4">Manage X-Ray Images</h2>


            <input type="file" accept="image/*" onChange={(e) => setSelectedFile(e.target.files?.[0] || null)}/>
            <Button
                variant="contained"
                color="primary"
                startIcon={<CloudUpload/>}
                onClick={handleUpload}
                className="ml-2">
                Upload
            </Button>

            <div className="xray-gallery">
                {images.map((image) => (
                    <div key={image.id} className="xray-item">
                        <img src={`data:${image.fileType};base64,${image.data}`} alt={image.fileName}/>
                        <p>{image.fileName}</p>
                        <Button
                            startIcon={<Delete/>}
                            onClick={() => handleDelete(image.id)}>
                            Delete
                        </Button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default XRayDocuments;
