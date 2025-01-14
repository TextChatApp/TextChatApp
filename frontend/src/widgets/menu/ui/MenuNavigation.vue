<template>
  <div class="flex gap-8 justify-center items-center sm:flex-col w-full">
    <div class="flex justify-center items-center icon" :class="[{ active: isActive('chats') }]">
      <router-link :to="'/'"><IconHome :isActive="isActive('chats')"></IconHome></router-link>
    </div>
    <div class="flex justify-center items-center icon" :class="[{ active: isActive('settings') }]">
      <router-link :to="'#'"><IconSettings></IconSettings></router-link>
    </div>
    <div class="flex justify-center items-center icon" :class="[{ active: isActive('chat') }]">
      <router-link :to="'/chat/38'"
        ><IconChats :isActive="isActive('chat')"></IconChats
      ></router-link>
    </div>
    <div class="flex justify-center items-center icon" :class="[{ active: isActive('users') }]">
      <router-link to="/users"><IconUsers :isActive="isActive('users')"></IconUsers></router-link>
    </div>
    <!-- Переходим по ссылке на виджет и исполльзуем фичу на создание канала -->
  </div>
</template>

<script setup lang="ts">
import {
  IconHome,
  IconChats,
  IconSettings,
  IconUsers,
  IconCreateServer
} from '@/shared/ui/icons/menu'
import { onMounted, ref } from 'vue'
import { RouterLink } from 'vue-router'
import { useRoute } from 'vue-router'
import { http } from '@/shared/api'
import Popup from '@/shared/ui/popup'

const router = useRoute()

const popupRef = ref()

const isActive = (name: string) => {
  return router.name === name
}

const openPopup = () => {
  popupRef.value?.open()
}

// const createServer = async () => {
//   try {
//     const { data } = await http.post(
//       '/servers/create',
//       {
//         name: 'ppdpd',
//         description: 'dsdsdsdsdsd'
//       },
//       {
//         headers: {
//           Authorization: localStorage.getItem('token')
//         }
//       }
//     )
//     console.log(data)
//   } catch (err) {
//     console.log(err)
//   }
// }
</script>

<style scoped>
.icon {
  width: 46px;
  height: 46px;
}
.active {
  border-radius: 14px;

  @media (min-width: 500px) {
    background-color: #5051f9;
  }
}
</style>
