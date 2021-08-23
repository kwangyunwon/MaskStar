<template>
  <v-dialog
    v-model="dialog"
    transition="fab-transition"
    max-width="500"
  >
  <template #activator="{ on, attrs }">
          <v-list-item-title v-bind="attrs" v-on="on">로그인</v-list-item-title>
      </template>
     <v-card>
        <v-card-title>
          <span class="text-h5">로그인</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-form ref="form" v-model="valid" lazy-validation>
            <v-row>
              <v-col
                cols="12"
                sm="12"
              >
                <v-text-field
                  v-model="login_id"
                  label="아이디*"
                  :rules="id_rule"
                  required
                ></v-text-field>
                <v-text-field
                  v-model="login_pwd"
                  label="비밀번호"
                  type="password"
                  :rules="pwd_rule"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="blue darken-1"
            text
            @click="login"
          >
            로그인
          </v-btn>
          <v-btn
            color="blue darken-1"
            text
            @click="close"
          >
            취소
          </v-btn>
          
        </v-card-actions>
      </v-card>
  </v-dialog>
</template>

<script>
import axios from 'axios';
const storage = window.localStorage
export default {
  data: () => ({
    valid: true,
    dialog: false,
    login_id:'',
    login_pwd:'',
    id_rule: [
        v => !!v || '아이디는 필수 입력사항입니다.',
    ],
    pwd_rule:[
       v => !!v || '비밀번호는 필수 입력사항입니다.',
    ],
  }),
  created() {
      this.$emit('create_routerview', 'Login');
  },
  methods:{
    login(){
      const loginForm={
        username:this.login_id,
        password:this.login_pwd,
      }

      this.$refs.form.validate();
      axios.post('http://api.maskstar.kr/authenticate',loginForm
      ).then(response=>{
        if(response.status!=200){
          alert("로그인 실패하였습니다")
        }
        
        
        storage.setItem('token',response.data['token'])
        storage.setItem('id',response.data['id'])
        storage.setItem('userIdx',response.data['userIdx'])
        storage.setItem('nickname',response.data['nickname'])
        storage.setItem('date',response.data['date'])
        
        alert("로그인이 되었습니다.")
        this.dialog=false
        location.reload();
        
      }).catch((ex)=>{
        console.log(ex)
        alert("로그인 실패하였습니다")
      })
    },
    close(){
      this.dialog=false
      this.$refs.form.reset();
      this.$refs.form.resetValidation();
    },

  }
}
</script>

<style lang="scss">


</style>
