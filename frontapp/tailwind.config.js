/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js,tsx,ts,jsx}", "./node_modules/tw-elements/js/**/*.js"],
  theme: {
    extend: {},
  },
  plugins: [
    require('@tailwindcss/forms', "tw-elements/plugin.cjs")
  ],
  darkMode: "class"
}