<template>
  <form @submit.prevent="submitForm">
    <div class="mb-8">
      <DefaultInput
        :placeholder="'Server name'"
        class="mb-3"
        @blur="v$.name.$touch"
        :error="v$.name.$error"
        v-model="serverStore.serverData.name"
      ></DefaultInput>
      <DefaultInput
        :placeholder="'Description'"
        @blur="v$.description.$touch"
        :error="v$.description.$error"
        v-model="serverStore.serverData.description"
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
import { useServerCreatingStore } from '@/features/create-server'
import { storeToRefs } from 'pinia'
import useVuelidate from '@vuelidate/core'
import { createServerScheme } from '../model/validation'

const serverStore = useServerCreatingStore()

const { serverData, loading } = storeToRefs(serverStore)

const v$ = useVuelidate(createServerScheme, serverData)

const emits = defineEmits(['close-popup'])

const submitForm = async () => {
  const result = await v$.value.$validate()
  if (result) {
    serverStore.createServer()
    emits('close-popup')
  }
}
</script>

<style></style>
