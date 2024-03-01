<template>
  <b-container fluid>
    <b-row>
      <b-col>
        <h1 style="text-align: center; margin-top: 10px">Books</h1>
      </b-col>
    </b-row>
    <hr v-show="carouselVisible" />
    <b-row class="justify-content-center">
      <b-carousel
        id="carousel-books"
        v-model="slide"
        :interval="4000"
        controls
        indicators
        background="#ababab"
        img-width="250"
        img-height="350"
        style="
          text-shadow: 1px 1px 2px #333;
          margin-top: 10px;
          margin-bottom: 10px;
        "
        @sliding-start="onSlideStart"
        @sliding-end="onSlideEnd"
        v-show="carouselVisible"
      >
        <b-carousel-slide
          v-for="(book, index) in books"
          :key="index"
          :text="book.nameBook"
          :img-src="
            book.image ? book.image : 'https://via.placeholder.com/250x350'
          "
          style="max-width: 250px; max-height: 350px"
        ></b-carousel-slide>
      </b-carousel>
    </b-row>
    <hr />
    <b-row>
      <b-col class="d-flex justify-content-center">
        <b-button
          variant="info"
          @click="openAddBookModal()"
          style="margin: 10px"
        >
          Añadir libro
        </b-button>
      </b-col>
    </b-row>
    <hr />
    <b-row class="justify-content-center mt-3">
      <b-col cols="2">
        <b-button @click="getBooksByAuthor()"
          >Odernar por autor alfabeticamente</b-button
        >
      </b-col>
      <b-col cols="2">
        <b-button @click="getBooksByDateDesc()"
          >Odernar por fecha descendentemente</b-button
        >
      </b-col>
      <b-col cols="2">
        <b-button @click="getBooksByImage()"
          >Mostrar solo los que tienen imagen</b-button
        >
      </b-col>
    </b-row>
    <hr />
    <b-row class="justify-content-center">
      <b-card
        v-for="(book, index) in books"
        :key="index"
        :title="book.nameBook"
        :img-src="book.image ? book.image : 'https://via.placeholder.com/350'"
        img-alt="imagen"
        img-top
        img-height="350px"
        style="max-width: 15rem; margin: 10px"
        class="mb-2 card"
        v-bind:style="{ animationDelay: `${index * 0.1}s` }"
      >
        <b-card-text>
          <b-row>
            <b-col>
              <b-card-text>Autor: {{ book.author }}</b-card-text>
              <b-card-text
                >Año de publicación:
                {{ formatDate(book.issueDate) }}</b-card-text
              >
            </b-col>
          </b-row>
        </b-card-text>
      </b-card>
    </b-row>

    <AddBookModal @bookAdded="refreshBookList" />
    <EditBookModal @bookEdited="refreshBookList" :book="selectedBook" />
  </b-container>
</template>

<script>
import Vue from "vue";
import bookService from "../books/BookService";

export default Vue.extend({
  components: {
    AddBookModal: () => import("../components/AddBookModal.vue"),
    EditBookModal: () => import("../components/EditBookModal.vue"),
  },
  data() {
    return {
      books: [],
      selectedBook: null,
      slide: 0,
      sliding: null,
      carouselVisible: true,
      scrollTimer: null,
    };
  },
  mounted() {
    this.getBooks();
    window.addEventListener("scroll", this.handleScroll);
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.handleScroll);
  },
  methods: {
    handleDragStart(event) {
      event.dataTransfer.setData("text/plain", "form-data");
      this.dragging = true;
    },
    handleDragEnd() {
      this.dragging = false;
    },
    handleDrop(event) {
      if (this.dragging) {
        this.addBook();
      }
    },
    async getBooks() {
      try {
        const data = await bookService.getBooks();
        this.books = data.data;
      } catch (error) {
        console.log(error);
      }
    },
    async getBooksByAuthor() {
      try {
        const data = await bookService.getBooksByAuthor();
        this.books = data;
      } catch (error) {
        console.log(error);
      }
    },
    async getBooksByDateDesc() {
      try {
        const data = await bookService.getBooksByDateDesc();
        this.books = data;
      } catch (error) {
        console.log(error);
      }
    },
    async getBooksByImage() {
      try {
        const data = await bookService.getBooksByImage();
        console.log(data);
        this.books = data;
      } catch (error) {
        console.log(error);
      }
    },
    openAddBookModal() {
      this.$nextTick(() => {
        this.$bvModal.show("addBookModal");
      });
    },
    openEditBookModal(book) {
      this.selectedBook = book;
      this.selectedBook.issueDate = new Date(book.issueDate).getFullYear();

      this.$nextTick(() => {
        this.$bvModal.show("editBookModal");
      });
    },
    refreshBookList() {
      this.getBooks();
    },
    deleteBook(index) {
      try {
        const book = this.books[index];
        bookService.deleteBook(book.id);
        this.books.splice(index, 1);
      } catch (error) {
        console.log(error);
      }
    },
    formatDate(date) {
      return new Date(date).getFullYear();
    },
    handleScroll() {
      if (this.scrollTimer) {
        clearTimeout(this.scrollTimer);
      }

      this.scrollTimer = setTimeout(() => {
        this.carouselVisible = window.scrollY < 200;
      }, 250);
    },
    onSlideStart(slide) {
      this.sliding = true;
    },
    onSlideEnd(slide) {
      this.sliding = false;
    },
  },
  computed: {
    listOne() {
      return this.books.filter((item) => item.list === 1);
    },
    listTwo() {
      return this.books.filter((item) => item.list === 2);
    },
  },
});
</script>

<style>
.card {
  animation: bounce-in 0.5s ease forwards;
  opacity: 0;
  transform: translateY(-20px);
}

@keyframes bounce-in {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
