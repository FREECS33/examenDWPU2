<template>
  <b-modal
    id="editBookModal"
    title="Editar Libro"
    no-close-on-backdrop
    hide-header-close
    no-close-on-esc
    ok-title="Editar"
    ok-variant="success"
    cancel-title="Cancelar"
    cancel-variant="danger"
    @ok="editBook"
    :ok-disabled="book.title.trim() === ''"
    @cancel="closeModal"
  >
    <b-container fluid>
      <form @submit.prevent="editBook">
        <b-form-group label="Título:" label-for="title">
          <b-form-input v-model="book.nameBook" id="title" required />
        </b-form-group>
        <b-form-group label="Autor:" label-for="author">
          <b-form-input v-model="book.author" id="author" required />
        </b-form-group>
        <b-form-group label="Año de publicación:" label-for="issueDate">
          <b-form-input
            v-model="book.issueDate"
            id="issueDate"
            type="number"
            required
          />
        </b-form-group>
        <b-form-group label="Imagen:" label-for="image">
          <b-form-input v-model="book.image" id="image" required />
        </b-form-group>
      </form>
    </b-container>
  </b-modal>
</template>
  
  <script>
import Vue from "vue";
import bookService from "../books/BookService";

export default Vue.extend({
  props: {
    book: Object,
  },
  data() {
    return {
      isLoading: false,
    };
  },
  mounted() {
    this.closeModal();
  },
  methods: {
    async editBook() {
      this.isLoading = true;
      try {
        const publicationDate = new Date(this.book.issueDate, 0, 1);
        this.book.issueDate = publicationDate;
        const response = await bookService.updateBook(this.book);

        if (!response.error) {
          this.isLoading = false;
          this.$emit("bookEdited");
          this.closeModal();
        }
      } catch (error) {
        console.log(error);
        this.isLoading = false;
      }
    },
    closeModal() {
      this.$bvModal.hide("editBookModal");
    },
  },
});
</script>