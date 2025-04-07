import {useEffect, useState} from "react";
import axios from "axios";

type XRayImage = {
    id: string;
    fileName: string;
    fileType: string;
    data: string;
}

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
            <button onClick={handleUpload} className="ml-2 p-2 bg-blue-500 text-white rounded">
                Upload
            </button>

            <div className="xray-gallery">
                {images.map((image) => (
                    <div key={image.id} className="xray-item">
                        <img src={`data:${image.fileType};base64,${image.data}`} alt={image.fileName}/>
                        <p>{image.fileName}</p>
                        <button onClick={() => handleDelete(image.id)}>
                            Delete
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default XRayDocuments;
