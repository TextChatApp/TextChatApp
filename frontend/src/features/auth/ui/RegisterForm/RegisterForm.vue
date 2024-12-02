<template>
  <div class="flex flex-col gap-8 items-center">
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
    <Loader v-if="loading" :isDark="true"></Loader>
    <ButtonLogin
      class="text-center"
      :text="'Register'"
      @click="signUp()"
      v-if="!loading"
    ></ButtonLogin>
  </div>
</template>

<script setup lang="ts">
import humanIcon from '@/shared/assets/image/human-icon.png'
import passwordIcon from '@/shared/assets/image/password-icon.png'
import mailIcon from '@/shared/assets/image/mail-icon.png'
import ButtonLogin from '@/shared/ui/buttons/ButtonLogin'
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
    console.log(data)
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
