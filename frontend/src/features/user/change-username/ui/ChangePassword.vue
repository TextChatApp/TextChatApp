<template>
  <form @submit.prevent="submitForm">
    <div class="mb-8">
      <DefaultInput
        :placeholder="'New password'"
        class="mb-3"
        @blur="v$.password.$touch"
        :error="v$.password.$error"
        v-model="form.password"
      ></DefaultInput>
      <DefaultInput
        :placeholder="'Repeat password'"
        class="mb-3"
        @blur="v$.confirmPassword.$touch"
        :error="v$.confirmPassword.$error"
        v-model="form.confirmPassword"
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
</template>

<script setup lang="ts">
import DefaultButton from '@/shared/ui/buttons/DefaultButton'
import { DefaultInput } from '@/shared/ui/inputs/DefaultInput'
import { useChangeUser } from '../model'
import { storeToRefs } from 'pinia'
import useVuelidate from '@vuelidate/core'
import { required, minLength, sameAs } from '@vuelidate/validators'
import { ref, reactive, computed } from 'vue'
import { useUser } from '@/entities/user'

const changeUserStore = useChangeUser()
const userStore = useUser()

const { loading } = storeToRefs(changeUserStore)

const form = reactive({
  password: '',
  confirmPassword: ''
})

// Валидация
const rules = {
  password: { required, minLength: minLength(6) },
  confirmPassword: {
    required,
    sameAsPassword: sameAs(computed(() => form.password))
  }
}

const v$ = useVuelidate(rules, form)

const emits = defineEmits(['close-popup'])

const submitForm = async () => {
  const result = await v$.value.$validate()
  if (result) {
    changeUserStore.changeProfile(form)
    emits('close-popup')
  }
}
</script>

<style></style>
