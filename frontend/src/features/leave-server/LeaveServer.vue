<template>
  <div class="flex items-center gap-2 transition-all cursor-pointer w-fit">
    <button class="inline-flex items-center gap-1 text-red-400" @click="leaveFromServer">
      <LeaveIcon :size="30" :color="'#fff'"></LeaveIcon>Leave
    </button>
  </div>
</template>

<script setup lang="ts">
import { LeaveIcon } from '@/shared/ui/icons/headerIcons'
import { leaveFromServerQuery } from '@/entities/server'
import { useUser } from '@/entities/user'
import { storeToRefs } from 'pinia'
import { useRouter } from 'vue-router'

const userStore = useUser()
const router = useRouter()

const { getUserId } = storeToRefs(userStore)

const props = defineProps({
  serverId: Number
})

const leaveFromServer = async () => {
  try {
    const { data } = await leaveFromServerQuery(props.serverId)
    router.push('/')
  } catch (err) {
    console.log(err)
  }
}
</script>

<style scoped>
div:hover {
  transform: scale(1.05);
}
</style>
