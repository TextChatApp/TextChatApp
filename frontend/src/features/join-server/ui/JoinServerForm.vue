<template>
  <form @submit.prevent="submitForm">
    <div class="mb-8">
      <DefaultInput
        :placeholder="'Invite code'"
        class="mb-3"
        @blur="v$.code.$touch"
        :error="v$.code.$error"
        v-model="joinServerStore.dataStore.code"
      ></DefaultInput>
    </div>
    <DefaultButton
      :loading="loading"
      :text="'Join'"
      class="md:w-full w-1/2"
      type="submit"
      :disabled="v$.$invalid"
    ></DefaultButton>
  </form>
</template>

<script setup lang="ts">
import DefaultButton from '@/shared/ui/buttons/DefaultButton'
import { DefaultInput } from '@/shared/ui/inputs/DefaultInput'
import { useJoinServerStore } from '../model/store'
import { storeToRefs } from 'pinia'
import { joinToServerScheme } from '../model/validation'
import useVuelidate from '@vuelidate/core'

const joinServerStore = useJoinServerStore()
const { dataStore, loading, errors } = storeToRefs(joinServerStore)

const v$ = useVuelidate(joinToServerScheme, dataStore)

const emits = defineEmits(['close-popup'])

const submitForm = async () => {
  const result = await v$.value.$validate()
  if (result) {
    await joinServerStore.joinServer()
    if (!errors.value) {
      console.log(errors.value)
      emits('close-popup')
    } else {
      console.log(errors.value)
    }
  }
}
</script>

<style></style>
