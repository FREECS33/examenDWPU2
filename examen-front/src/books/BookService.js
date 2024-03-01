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

const getBooksByAuthor = async () => {
  try {
    const response = await axios.get(`${API_URL}autor`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

const getBooksByDateDesc = async () => {
  try {
    const response = await axios.get(`${API_URL}fecha-desc`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

const getBooksByImage = async () => {
  try {
    const response = await axios.get(`${API_URL}con-imagen`);
    return response.data;
  } catch (error) {
    console.log(error);
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
  getBooksByAuthor,
  getBooksByDateDesc,
  getBooksByImage,
  getBook,
  addBook,
  updateBook,
  deleteBook,
};
