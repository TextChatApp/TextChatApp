<template>
  <form @submit.prevent="submitForm">
    <div class="mb-8">
      <DefaultInput
        :placeholder="'Room name'"
        class="mb-3"
        @blur="v$.name.$touch"
        :error="v$.name.$error"
        v-model="roomData.name"
      ></DefaultInput>
    </div>
    <DefaultButton
      :loading="loading"
      :text="'Create'"
      class="md:w-full w-1/2"
      type="submit"
      :disabled="v$.$invalid"
    ></DefaultButton>
  </form>
  <!-- <button @click="createServer()">Create Server</button> -->
</template>

<script setup lang="ts">
import DefaultButton from '@/shared/ui/buttons/DefaultButton'
import { DefaultInput } from '@/shared/ui/inputs/DefaultInput'
import { useRoomCreatingStore } from '../model/store'
import { storeToRefs } from 'pinia'
import useVuelidate from '@vuelidate/core'
import { createRoomScheme } from '../model/validation'
import { useRoute } from 'vue-router'

const roomStore = useRoomCreatingStore()

const { roomData, loading } = storeToRefs(roomStore)

const props = defineProps({
  serverId: Number
})

const v$ = useVuelidate(createRoomScheme, roomData)
const router = useRoute()

const emits = defineEmits(['close-popup'])

const submitForm = async () => {
  const result = await v$.value.$validate()
  if (result) {
    roomStore.createRoom(props.serverId)
    emits('close-popup')
  }
}
</script>

<style></style>
