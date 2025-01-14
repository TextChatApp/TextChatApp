<template>
  <form @submit.prevent="submitForm">
    <div class="mb-8">
      <DefaultInput
        :placeholder="'Enter new name'"
        class="mb-3"
        @blur="v$.name.$touch"
        :error="v$.name.$error"
        v-model="changeNameStore.dataStore.name"
      ></DefaultInput>
    </div>
    <DefaultButton
      :loading="loading"
      :text="'Change'"
      class="md:w-full w-1/2"
      type="submit"
      :disabled="v$.$invalid"
    ></DefaultButton>
  </form>
</template>

<script setup lang="ts">
import DefaultButton from '@/shared/ui/buttons/DefaultButton'
import { DefaultInput } from '@/shared/ui/inputs/DefaultInput'
import { useChangeNameStore } from '../model'
import { storeToRefs } from 'pinia'
import { changeRoomNameScheme } from '../model/validation'
import useVuelidate from '@vuelidate/core'

const changeNameStore = useChangeNameStore()
const { dataStore, loading, errors } = storeToRefs(changeNameStore)

const props = defineProps({
  roomId: Number,
  serverId: Number
})

const v$ = useVuelidate(changeRoomNameScheme, dataStore)

const emits = defineEmits(['close-popup'])

const submitForm = async () => {
  const result = await v$.value.$validate()
  if (result) {
    await changeNameStore.changeName(props.roomId, props.serverId)
    if (!errors.value) {
      emits('close-popup')
    } else {
      console.log(errors.value)
    }
  }
}
</script>

<style></style>
