<template>
    <b-modal
      id="addBookModal"
      title="Añadir Libro"
      no-close-on-backdrop
      hide-header-close
      no-close-on-esc
      ok-title="Añadir"
      ok-variant="success"
      cancel-title="Cancelar"
      cancel-variant="danger"
      @ok="addBook"
      :ok-disabled="!newBook.nameBook"
      @cancel="closeModal"
    >
      <b-container fluid>
        <form @submit.prevent="addBook">
          <b-form-group label="Título:" label-for="title">
            <b-form-input v-model="newBook.nameBook" id="title" required />
          </b-form-group>
          <b-form-group label="Autor:" label-for="author">
            <b-form-input v-model="newBook.author" id="author" required />
          </b-form-group>
          <b-form-group label="Año de publicación:" label-for="issueDate">
            <b-form-input
              v-model="newBook.issueDate"
              id="issueDate"
              type="number"
              required
            />
          </b-form-group>
          <b-form-group label="Imagen:" label-for="image">
            <b-form-input v-model="newBook.image" id="image" required />
          </b-form-group>
        </form>
      </b-container>
    </b-modal>
  </template>
    
    <script>
  import Vue from "vue";
  import bookService from "../books/BookService";
  
  export default Vue.extend({
    data() {
      return {
        newBook: {
          nameBook: "",
          author: "",
          issueDate: null,
          image: "",
        },
      };
    },
    mounted() {
      this.closeModal();
      this.limpiar();
    },
    methods: {
      async addBook() {
        this.isLoading = true;
        try {
          const publicationDate = new Date(this.newBook.issueDate, 0, 1);
          this.newBook.issueDate = publicationDate;
          const response = await bookService.addBook(this.newBook);
  
          if (!response.error) {
            this.isLoading = false;
            this.limpiar();
            this.$emit("bookAdded");
            this.$bvModal.hide("addBookModal");
          }
        } catch (error) {
          console.log(error);
          this.isLoading = false;
        }
      },
      closeModal() {
        this.limpiar();
        this.$bvModal.hide("addBookModal");
      },
      limpiar() {
        this.newBook = {
          title: "",
          autor: {
            id: null,
          },
          categoria: {
            id: null,
          },
          issueDate: null,
          image: "",
        };
      },
    },
  });
  </script>