<template>
  <b-container fluid>
    <b-row class="justify-content-center">
      <b-carousel
        id="carousel-books"
        v-model="slide"
        :interval="4000"
        controls
        indicators
        background="#ababab"
        img-width="480"
        img-height="480"
        style="text-shadow: 1px 1px 2px #333"
        @sliding-start="onSlideStart"
        @sliding-end="onSlideEnd"
      >
        <b-carousel-slide
          v-for="(book, index) in books"
          :key="index"
          :text="book.nameBook"
          :img-src="book.image"
          :img-blank="book.image"
          style="max-width: 250px; max-height: 250px; "
        ></b-carousel-slide>
      </b-carousel>
    </b-row>
    <b-row class="justify-content-center">
      <b-col>
        <b-button @click="openAddBookModal()">Agregar libro</b-button>
      </b-col>
    </b-row>

    <b-row class="justify-content-center mt-3">
      <b-col cols="5" v-if="filter === 'title' || filter === 'author'">
        <b-form-group label="Buscar libro:" label-for="searchInput">
          <b-form-input
            v-model="textFilter"
            placeholder="Mil Noches"
            type="text"
            id="searchInput"
            @input="getBooksByTitleOrAuthor()"
          />
        </b-form-group>
      </b-col>
      <b-col cols="5" v-else>
        <b-form-group label="Order por año de publicación:">
          <b-button @click="getBooksByDateDesc()"
            >Odernar descendentemente</b-button
          >
        </b-form-group>
      </b-col>
      <b-col cols="5">
        <b-form-group label="Realizar busqueda por:" label-for="searchSelect">
          <b-form-select
            v-model="filter"
            :options="options"
            id="searchSelect"
          />
        </b-form-group>
      </b-col>
    </b-row>
    <b-row class="justify-content-center">
      <b-card
        v-for="(book, index) in books"
        :key="index"
        :title="book.nameBook"
        :img-src="book.image"
        img-alt="imagen"
        img-top
        img-height="250px"
        style="max-width: 15rem; margin: 10px"
        class="mb-2 card"
        v-bind:style="{ animationDelay: `${index * 0.1}s` }"
      >
        <b-card-text>
          <b-row>
            <b-col>
              <b-card-text>Autor: {{ book.auhtor }}</b-card-text>
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
      filter: "title",
      options: [
        { value: "title", text: "Título" },
        { value: "author", text: "Autor" },
        { value: "dateDesc", text: "Fechas Descendentes" },
      ],
      textFilter: "",
      slide: 0,
      sliding: null,
    };
  },
  mounted() {
    this.getBooks();
    window.addEventListener("scroll", this.handleScroll);
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
        this.isLoading = false;
      } catch (error) {
        console.log(error);
        this.isLoading = false;
      }
    },
    async getBooksByTitleOrAuthor() {
      if (this.filter === "title") {
        try {
          const data = await bookService.getBooksByTitle(this.textFilter);
          this.books = data;
        } catch (error) {
          console.log(error);
        }
      } else if (this.filter === "author") {
        try {
          const data = await bookService.getBooksByAuthor(this.textFilter);
          this.books = data;
        } catch (error) {
          console.log(error);
        }
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
      this.formVisible = window.scrollY < 105;
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
  animation: fade-in 0.5s ease forwards;
  opacity: 0;
}

@keyframes fade-in {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>