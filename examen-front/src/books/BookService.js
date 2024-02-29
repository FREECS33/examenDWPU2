import axios from "axios";

let API_URL = "http://localhost:8080/api/books/";

const getBooks = async () => {
    try {
        const response = await axios.get(API_URL);
        return response.data;
    } catch (error) {
        throw error;
    }
};

const getBooksByTitle = async (nombre) => {
    try {
        const response = await axios.get(
            `${API_URL}buscarPorNombre?nombre=${nombre}`
        );
        return response.data;
    } catch (error) {
        throw error;
    }
};

const getBooksByAuthor = async (autor) => {
    try {
        const response = await axios.get(`${API_URL}buscarPorAutor?autor=${autor}`);
        return response.data;
    } catch (error) {
        throw error;
    }
};

const getBooksByDateDesc = async () => {
    try {
        const response = await axios.get(API_URL + "ordenadasPorFechaDesc");
        return response.data;
    } catch (error) {
        throw error;
    }
};

const getBook = async (id) => {
    try {
        const response = await axios.get(`${API_URL}/${id}`);
        return response.data;
    } catch (error) {
        throw error;
    }
};

const addBook = async (book) => {
    try {
        const response = await axios.post(API_URL, book);
        return response.data;
    } catch (error) {
        throw error;
    }
};

const updateBook = async (book) => {
    try {
        const id = book.id;
        const response = await axios.put(API_URL + id, book);
        return response.data;
    } catch (error) {
        throw error;
    }
};

const deleteBook = async (id) => {
    try {
        const response = await axios.delete(API_URL + id);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export default {
    getBooks,
    getBooksByTitle,
    getBooksByAuthor,
    getBooksByDateDesc,
    getBook,
    addBook,
    updateBook,
    deleteBook,
};
