<template>
  <!-- <Menu></Menu>
  <Header></Header>
  <div
    class="px-7 pb-24 w-full mx-auto sm:pl-24 sm:pt-0 sm:pr-0 sm:pb-0 sm:m-0 bg-chats-bg flex chats-container"
  >
    <Chats class="chat-list chat-list-container lg:w-96"></Chats>
    <div class="bg-black chat w-full h-full hidden lg:block flex-1 chat-list-container">
      <div class="w-full">
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Nobis voluptatibus doloremque,
        consequuntur minima reiciendis maxime quasi expedita modi libero labore, quam asperiores
        alias ipsam molestias. Consequuntur similique rem debitis voluptates?
      </div>
    </div>
  </div> -->
</template>

<script setup lang="ts">
import Menu from '@/widgets/menu'
import Header from '@/widgets/header'
</script>

<style scoped>
.chat-list-container {
  min-height: calc(100vh - 80px); /* Ограничение по высоте, в зависимости от вашего layout */
  max-height: 100vh;
  overflow-y: auto; /* Вертикальная прокрутка */
}

.chats-container {
  height: calc(100vh - 80px);
  width: 100%;
}
</style>
