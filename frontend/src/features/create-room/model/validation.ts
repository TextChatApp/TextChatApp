import { required, email, minLength, numeric } from '@vuelidate/validators'

export const createRoomScheme = {
  name: { required }
}
