import {useEffect, useState} from "react";
import axios from "axios";
interface InvoiceDocument {
    id: string;
    fileName: string;
    fileType: string;
    uploadDate: string;
    data: string;
}

const DocumentsPage = () => {
    const [documents, setDocuments] = useState<InvoiceDocument[]>([]);
    const [selectedFile, setSelectedFile] = useState<File | null>(null);

    useEffect(() => {
        axios.get<InvoiceDocument[]>("/api/documents")
            .then((res) => setDocuments(res.data))
            .catch((err) => console.error("Error fetching documents:", err));
    }, []);

    const handleUpload = async () => {
        if (!selectedFile) return;

        const formData = new FormData();
        formData.append("file", selectedFile);

        try {
            const response = await axios.post("/api/documents", formData, {
                headers: {"Content-Type": "multipart/form-data"}
            });
            setDocuments([...documents, response.data]);
            setSelectedFile(null);
        } catch (error) {
            console.error("Error uploading document:", error);
        }
    };

    const handleDelete = async (id: string) => {
        try {
            await axios.delete(`/api/documents/${id}`);
            setDocuments(documents.filter((doc) => doc.id !== id));
        } catch (error) {
            console.error("Error deleting document:", error);
        }
    };

    return (
        <div className="p-4">
            <h2 className="text-xl font-bold mb-4">Manage Documents</h2>

            <input
                type="file"
                accept=".pdf,.doc,.docx,.txt,.xls,.xlsx,.csv,.png,.jpeg,.jpg"
                onChange={(e) => setSelectedFile(e.target.files?.[0] || null)}
            />
            <button onClick={handleUpload} className="ml-2 p-2 bg-blue-500 text-white rounded">
                Upload
            </button>

            <div className="xray-gallery">
                {documents.map((document) => (
                    <div key={document.id} className="xray-item">
                        {document.fileType.includes("image") ? (
                            <img
                                src={`data:${document.fileType};base64,${document.data}`}
                                alt={document.fileName}
                            />
                        ) : (
                            <p>{document.fileName}</p>
                        )}
                        <p>{document.fileName}</p>
                        <button onClick={() => handleDelete(document.id)}>
                            Delete
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default DocumentsPage;





