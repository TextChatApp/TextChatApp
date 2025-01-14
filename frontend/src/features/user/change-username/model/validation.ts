import { required, email, minLength, numeric, sameAs } from '@vuelidate/validators'

export const changeUsernameScheme = {
  username: { required }
}

export const changeEmailScheme = {
  email: { required, email }
}
