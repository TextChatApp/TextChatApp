<template>
  <div class="w-full">
    <form @submit.prevent="uploadAvatar" class="w-full">
      <div class="flex justify-center">
        <label
          for="file-upload"
          class="cursor-pointer text-accent-main hover:text-blue-600 font-semibold"
        >
          Choose File
        </label>
        <input
          id="file-upload"
          type="file"
          @change="onFileChange"
          accept="image/*"
          class="opacity-0 absolute w-0 h-0"
        />
      </div>
      <button
        type="submit"
        class="w-fit bg-white text-black font-bold py-1 px-4 rounded-lg hover:bg-accent-main focus:outline-none focus:ring-2 focus:ring-blue-400 transition-all"
      >
        Upload
      </button>
    </form>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import axios, { type AxiosResponse } from 'axios'
import { useUser } from '@/entities/user'

const userStore = useUser()

const file = ref<File | null | undefined>(null)
const token = localStorage.getItem('token')

const onFileChange = (event: Event): void => {
  const target = event.target as HTMLInputElement
  const selectedFile = target.files?.[0] // Получаем первый выбранный файл

  if (selectedFile && !selectedFile.type.startsWith('image/')) {
    alert('Пожалуйста, выберите файл изображения.')
    file.value = null // Очистка, если файл не изображение
  } else {
    file.value = selectedFile
  }
}

// Метод для загрузки аватара
const uploadAvatar = async (): Promise<void> => {
  if (!file.value) {
    alert('Пожалуйста, выберите изображение для загрузки.')
    return
  }

  const formData = new FormData()
  formData.append('file', file.value)

  try {
    const response: AxiosResponse = await axios.post(
      'http://localhost:8080/api/users/me/avatar', // Замените на ваш URL API
      formData,
      {
        headers: {
          Authorization: token,
          'Content-Type': 'multipart/form-data'
        }
      }
    )
    console.log(response.data)
    userStore.setNewInfo(response.data)
    console.log('Аватар успешно загружен', response.data)
  } catch (error) {
    console.error('Ошибка при загрузке аватара', error)
  }
}
</script>
