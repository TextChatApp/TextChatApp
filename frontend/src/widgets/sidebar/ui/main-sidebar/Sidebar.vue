<template>
  <div class="overflow-y-auto w-full h-full md:w-80 px-7 py-5 bg-chats-bg overflow-x-hidden">
    <div v-if="!isRoomChoose">
      <h2 class="text-2xl sm:text-3xl font-bold mb-6">Messages</h2>
      <div class="mb-8 w-full"><ChatsInput :placeholder="'Search...'"></ChatsInput></div>
      <div class="mb-6 flex gap-2 flex-wrap items-center">
        <DefaultButton
          :text="'Create server'"
          class="md:w-full"
          @click="openPopup()"
        ></DefaultButton>
        <DefaultButton
          :text="'Join server'"
          class="md:w-full w-1/2"
          @click="openPopupJoin()"
        ></DefaultButton>
      </div>
      <div class="w-full mb-4">
        <div class="flex items-center gap-2 mb-4">
          <img :src="chatsIcon" alt="" />
          <h3>Personal chats</h3>
        </div>
        <div class="flex flex-col gap-5 items-center justify-center w-full mb-3" v-if="myChats">
          <!-- cards -->
          <router-link
            v-for="chat in myChats"
            :to="{ name: 'chat', params: { id: chat.id } }"
            class="w-full pt-2 pb-2 px-1 transition-all"
            :key="chat.id"
          >
            <SidibarChatItem :chat="chat"></SidibarChatItem>
          </router-link>
        </div>
        <Loader v-if="!myChats" class="pt-10"></Loader>
      </div>
      <div class="w-full">
        <div class="flex items-center gap-2 mb-4">
          <img :src="chatsIcon" alt="" />
          <h3>My servers</h3>
        </div>
        <div class="flex flex-col gap-5 items-center justify-center w-full mb-3" v-if="myChats">
          <!-- cards -->
          <!-- <router-link
            v-for="server in servers"
            :to="{ name: 'room', params: { id: server.id } }"
            class="w-full pt-2 pb-2 px-1 transition-all"
            :class="{}"
            :key="server.id"
          >
            <SidebarServerItem :server="server"></SidebarServerItem>
          </router-link> -->
          <div
            v-for="server in servers"
            class="w-full pt-2 pb-2 px-1 transition-all cursor-pointer active:translate-y-1"
            @click="openRoomChoose(server.rooms, server.id)"
            :class="{}"
            :key="server.id"
          >
            <SidebarServerItem :server="server"></SidebarServerItem>
          </div>
        </div>
        <Loader v-if="!myChats" class="pt-10"></Loader>
      </div>
      <Popup ref="popupRef">
        <template v-slot:header><h3 class="text-2xl mb-8">Creating server</h3></template>
        <template v-slot:default><CreateServer @close-popup="closePopup"></CreateServer></template>
      </Popup>
      <Popup ref="popupJoin">
        <template v-slot:header><h3 class="text-2xl mb-8">Join to server</h3></template>
        <template v-slot:default
          ><JoinServerForm @close-popup="closePopupJoin"></JoinServerForm
        ></template>
      </Popup>
    </div>
    <div class="">
      <transition name="slide">
        <SidebarRooms
          v-if="isRoomChoose"
          @close-room-choose="closeRoomChoose"
          :rooms="currentServerRooms"
          :serverId="currentServerId"
        ></SidebarRooms>
      </transition>
    </div>
  </div>
</template>

<script setup lang="ts">
import ChatsInput from '@/shared/ui/inputs/ChatsInput'
import chatsIcon from '@/shared/assets/image/chats.png'
import Loader from '@/shared/ui/loader/Loader.vue'

import { useRouter } from 'vue-router'
import { useChatStore } from '@/entities/chat'
import { useServerStore } from '@/entities/server'

import SidibarChatItem from './SidibarChatItem.vue'
import SidebarServerItem from './SidebarServerItem.vue'
import SidebarRooms from './SidebarRooms.vue'
import { onMounted, ref, provide } from 'vue'
import { storeToRefs } from 'pinia'

import { CreateServer } from '@/features/create-server'
import { JoinServerForm } from '@/features/join-server'
import DefaultButton from '@/shared/ui/buttons/DefaultButton'
import Popup from '@/shared/ui/popup'

const chatStore = useChatStore()
const serverStore = useServerStore()

const { myChats } = storeToRefs(chatStore)
const { servers } = storeToRefs(serverStore)

const router = useRouter()

const isRoomChoose = ref(false)

const currentServerRooms = ref()
const currentServerId = ref()

const popupRef = ref()
const popupJoin = ref()

const openPopup = () => {
  popupRef.value?.open()
}

const closePopup = () => {
  popupRef.value?.close()
}

const openPopupJoin = () => {
  popupJoin.value?.open()
}

const closePopupJoin = () => {
  popupJoin.value?.close()
}

const closeRoomChoose = () => {
  isRoomChoose.value = false
}

const openRoomChoose = (rooms: [], serverId: any) => {
  currentServerRooms.value = rooms
  currentServerId.value = serverId
  isRoomChoose.value = true
}

//запрос на получение чатов из энтити
onMounted(async () => {
  Promise.all([chatStore.getPrivateChats(), serverStore.getMyServers()])
})
</script>

<style scoped>
.active-chat {
  border-bottom: 3px solid #fff;
  border-radius: 1px;
}
.router-link-active {
  background-color: #131517;
  border-radius: 15px;
}
</style>
