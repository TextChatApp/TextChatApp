<template>
  <form class="flex flex-col items-center" @submit.prevent="signIn">
    <LoginInput class="w-full h-20" v-model="username" :placeholder="'Username'">
      <img :src="humanIcon" alt="humonIcon" />
    </LoginInput>
    <LoginInput
      class="w-full h-20"
      :inputType="'password'"
      v-model="password"
      :placeholder="'Password'"
    >
      <img :src="passwordIcon" alt="humonIcon" />
    </LoginInput>
    <ButtonLogin class="text-center" :text="'Login'" :loading="loading"></ButtonLogin>
    <!-- <Loader :isDark="true" v-if="loading"></Loader> -->
  </form>
</template>

<script setup lang="ts">
import humanIcon from '@/shared/assets/image/human-icon.png'
import passwordIcon from '@/shared/assets/image/password-icon.png'
import ButtonLogin from '@/shared/ui/buttons/ButtonLogin'
import LoginInput from '@/shared/ui/inputs/LoginInput'
import { useNotification } from '@kyvg/vue3-notification'
import Loader from '@/shared/ui/loader'
import { useUser } from '@/entities/user'
import { AxiosError } from 'axios'
import { login } from '@/shared/api'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const notification = useNotification()
const router = useRouter()

const userStore = useUser()

const username = ref('')
const password = ref('')

const loading = ref(false)

const signIn = async () => {
  try {
    loading.value = true
    const { data } = await login({
      username: username.value,
      password: password.value
    })

    userStore.setUserInfo(data)
    router.push('/')
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
