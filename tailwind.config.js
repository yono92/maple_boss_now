/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/main/resources/templates/**/*.{html,js}",
    "./src/main/resources/static/js/*.js",
    "./src/main/resources/static/css/*.css",
  ],
  theme: {
    extend: {},
  },
  plugins: [],
  darkMode: 'class', // 'class' 모드로 다크모드 활성화
}
