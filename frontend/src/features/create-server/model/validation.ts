import { required, email, minLength, numeric } from '@vuelidate/validators'

export const createServerScheme = {
  name: { required },
  description: { required }
}
