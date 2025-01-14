<template>
  <form class="flex flex-col gap-8 items-center" @submit.prevent="signUp">
    <LoginInput class="w-full" :inputType="'email'" :placeholder="'your email'" v-model="email">
      <img :src="mailIcon" alt="password" />
    </LoginInput>
    <LoginInput class="w-full" :placeholder="'your username'" v-model="username">
      <img :src="humanIcon" alt="humonIcon" />
    </LoginInput>
    <LoginInput
      class="w-full"
      :inputType="'password'"
      :placeholder="'your password'"
      v-model="password"
    >
      <img :src="passwordIcon" alt="password" />
    </LoginInput>
    <ButtonLogin class="text-center" :text="'Register'" :loading="loading"></ButtonLogin>
  </form>
</template>

<script setup lang="ts">
import humanIcon from '@/shared/assets/image/human-icon.png'
import passwordIcon from '@/shared/assets/image/password-icon.png'
import mailIcon from '@/shared/assets/image/mail-icon.png'
import ButtonLogin from '@/shared/ui/buttons/DefaultButton'
import LoginInput from '@/shared/ui/inputs/LoginInput'
import { AxiosError } from 'axios'
import Loader from '@/shared/ui/loader'
import { onMounted, ref } from 'vue'
import { useNotification } from '@kyvg/vue3-notification'
import { register } from '@/shared/api'
import { useRouter } from 'vue-router'

const username = ref('')
const email = ref('')
const password = ref()

const router = useRouter()
const notification = useNotification()

const loading = ref(false)

const signUp = async () => {
  try {
    loading.value = true
    const { data } = await register({
      username: username.value,
      email: email.value,
      password: password.value
    })
    loading.value = false
    router.push('/login')
  } catch (err) {
    if (err instanceof AxiosError) {
      notification.notify({
        title: 'Error',
        type: 'error',
        text: err?.response?.data
      })
    } else {
      notification.notify({
        title: 'Error',
        type: 'error',
        text: 'An unexpected error occurred' // или можно логировать ошибку
      })
    }
  } finally {
    loading.value = false
  }
}
</script>

<style></style>
