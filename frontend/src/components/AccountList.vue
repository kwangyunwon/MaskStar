<template>
  <v-menu offset-y>
        <template v-slot:activator="{ on }">
              <v-list-item-avatar v-on="on" size="60">
                <v-icon large style="cursor: pointer" color="white">mdi-account-circle</v-icon>
              </v-list-item-avatar>
        </template>

        <v-card>
          <v-list class="overflow-y-auto" style="max-height: calc(100vh - 100px)">
            <v-list-item>
              <v-list-item-avatar>
                <img src="@/assets/image/Profile.png" />
              </v-list-item-avatar>
              <v-list-item-content v-if="requestPermission">
                <v-list-item-title>로그인을 해주세요</v-list-item-title>
              </v-list-item-content>

              <v-list-item-content v-else>
                <v-list-item-title>{{value}}</v-list-item-title>
              </v-list-item-content>
          </v-list-item>

          <v-divider></v-divider>
          <div v-if="requestPermission">
            <v-list-item>
              <v-list-item-icon>
                <v-icon>mdi-account-box</v-icon>
              </v-list-item-icon>
              <v-list-item-title ><Login /></v-list-item-title>
            </v-list-item>
            <v-list-item>
              <v-list-item-icon>
                <v-icon>mdi-account-box</v-icon>
              </v-list-item-icon>
              <v-list-item-title><Register /></v-list-item-title>
            </v-list-item>
          </div>
          <div v-else>
            <v-list-item to="/mypage">
              <v-list-item-icon>
                <v-icon>mdi-account-box</v-icon>
              </v-list-item-icon>
              <v-list-item-title >마이 페이지</v-list-item-title>
            </v-list-item>
            <v-list-item @click="logout">
              <v-list-item-icon>
                <v-icon>mdi-login-variant</v-icon>
              </v-list-item-icon>
              <v-list-item-title >로그아웃</v-list-item-title>
            </v-list-item>
          </div>

            <v-divider></v-divider>
            <v-list-item @click="toggle_dark_mode">
              <v-list-item-icon >
                <v-icon >mdi-theme-light-dark</v-icon>
              </v-list-item-icon>
              <v-list-item-title>Dark Mode</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-card>
      </v-menu>
</template>

<script>
import Register from './Register.vue';
import Login from './Login.vue';


export default {
  components: { Register,Login},
  data(){
    return{
      get requestPermission(){
        return (localStorage.getItem('token')===null)
      },
      get value(){
        return localStorage.getItem('id')
      }
    }
  },
 methods: {
    toggle_dark_mode () {
        this.$vuetify.theme.dark = !this.$vuetify.theme.dark;
        localStorage.setItem("dark_theme", this.$vuetify.theme.dark.toString());
    },
    logout(){
      localStorage.clear()
      this.$router.replace('/');
      location.reload();
    }
    },
    created() {
      this.$emit('create_routerview', 'AccountList');
    },
    mounted() {
      const theme = localStorage.getItem("dark_theme");
        if (theme) {
            if (theme == "true") {
                this.$vuetify.theme.dark = true;
            } else {
                this.$vuetify.theme.dark = false;
            }
        }

    }
    
}
</script>

<style>
</style>