<template>
  <div>
    <div class="flex items-center gap-5 mb-6">
      <button
        @click="emits('close-room-choose')"
        class="text-2xl sm:text-3xl font-bold active:translate-y-1 transition-all"
      >
        <
      </button>
      <h2 class="text-2xl sm:text-3xl font-bold">Rooms</h2>
    </div>
    <div class="w-full">
      <div class="flex flex-col gap-5 items-center justify-center w-full mb-3">
        <!-- cards -->
        <div v-for="room in rooms" class="w-full pt-2 pb-2 px-1 flex items-center">
          <span>#</span>
          <router-link
            :to="{ name: 'room', params: { id: room?.id, serverId: serverId } }"
            class="w-full pt-2 pb-2 px-1 transition-all"
            :class="{}"
            :key="2"
          >
            <SidebarRoomItem :room="room"></SidebarRoomItem>
          </router-link>
        </div>
      </div>
      <!-- <Loader v-if="!myChats" class="pt-10"></Loader> -->
    </div>
  </div>
</template>

<script setup lang="ts">
import SidebarRoomItem from './SidebarRoomItem.vue'

const emits = defineEmits(['close-room-choose'])

interface Room {
  id?: number
  name?: string
}

interface Props {
  rooms?: Room[]
  serverId?: number
}

const props = defineProps<Props>()
</script>

<style scoped>
.router-link-active {
  text-decoration: underline;
  text-decoration-color: #5051f9;
  text-decoration-thickness: 3px;
}
</style>
