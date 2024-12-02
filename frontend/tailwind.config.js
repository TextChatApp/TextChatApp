/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        main: '#1E1F25',
        'accent-main': '#5051F9',
        'accent-main-100': 'rgba(80, 80, 249, 0.9)',
        'accent-blue': '#1DA7FF',
        'grey-main': '#A9ABAD',
        'black-bg': '#050505',
        'input-placeholder': '#9BABC5',
        'chats-bg': '#212229',
        'input-bg': '#282932'
      },
      borderWidth: {
        '2px': '2px' // добавляем 2 пикселя для borderWidth
      },
      spacing: {
        78: '78px'
      }
    }
  },
  plugins: []
}
