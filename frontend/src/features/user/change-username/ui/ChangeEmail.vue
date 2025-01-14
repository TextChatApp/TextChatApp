<template>
  <form @submit.prevent="submitForm">
    <div class="mb-8">
      <DefaultInput
        :placeholder="'New email'"
        class="mb-3"
        @blur="v$.email.$touch"
        :error="v$.email.$error"
        v-model="data.email"
      ></DefaultInput>
    </div>
    <DefaultButton
      :loading="loading"
      :text="'change'"
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
import { useChangeUser } from '../model'
import { storeToRefs } from 'pinia'
import useVuelidate from '@vuelidate/core'
import { useRoute } from 'vue-router'
import { changeEmailScheme } from '../model/validation'
import { ref } from 'vue'
import { useUser } from '@/entities/user'

const changeUserStore = useChangeUser()
const userStore = useUser()

const { loading } = storeToRefs(changeUserStore)

const data = ref({
  email: ''
})

const v$ = useVuelidate(changeEmailScheme, data)

const emits = defineEmits(['close-popup'])

const submitForm = async () => {
  const result = await v$.value.$validate()
  if (result && data.value) {
    changeUserStore.changeProfile(data.value)
    emits('close-popup')
  }
}
</script>

<style></style>
