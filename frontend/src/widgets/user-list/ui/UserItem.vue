<template>
  <div class="flex items-center gap-3">
    <div class="flex items-center gap-2 w-[150px]">
      <Avatar :url="user?.avatar" :size="32"></Avatar>
      <span
        class="text-xl font-bold truncate sm:truncate md:break-words lg:break-words overflow-hidden text-ellipsis"
      >
        {{ user?.username }}
      </span>
    </div>
    <button
      @click="startChat()"
      class="px-5 py-2 bg-main rounded-xl transition-all hover:scale-105 whitespace-nowrap"
    >
      Start chat
    </button>
  </div>
</template>

<script setup lang="ts">
import { type User } from '@/entities/user'
import UserIcon from '@/shared/assets/image/default-avatar-accent.png'
import Avatar from '@/shared/ui/avatar/Avatar.vue'
import { startPrivateChat } from '@/entities/chat'
import { useRouter } from 'vue-router'
import { useUser } from '@/entities/user'
import { storeToRefs } from 'pinia'

const userStore = useUser()
const { getUserId } = storeToRefs(userStore)

const router = useRouter()

const props = defineProps({
  user: Object
})

const startChat = async () => {
  try {
    if (props.user && getUserId.value) {
      console.log(props.user)
      const { data } = await startPrivateChat(getUserId.value, props.user?.id)
      router.push({ path: `/chat/${data}` })
    }
  } catch (err) {
    console.log(err)
  }
}
</script>

<style scoped>
.truncate {
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap; /* предотвращает перенос текста */
}

button {
  white-space: nowrap;
}

img {
  object-fit: cover;
}

/* Адаптивные стили для username */
@media (max-width: 768px) {
  span {
    text-overflow: ellipsis;
  }
}
</style>
