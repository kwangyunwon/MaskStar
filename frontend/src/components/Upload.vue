<template>
  <v-dialog v-model="dialog" transition="fab-transition" max-width="1050">
    <template #activator="{ on, attrs }">
      <v-list-item-title v-bind="attrs" v-on="on">업로드</v-list-item-title>
    </template>
    <v-card>
      <v-card-title>
        <span class="text-h5">업로드</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-form ref="form" v-model="valid" lazy-validation>
            <v-row>
              <v-col cols="4">
                <v-text-field
                  v-model="title"
                  label="노래*"
                  :rules="title_rule"
                  required
                >
                </v-text-field>
              </v-col>
                 <v-col cols="4">
                <v-text-field
                  v-model="singer"
                  label="가수*"
                  :rules="singer_rule"
                  required
                >
                </v-text-field>
              </v-col>

              <v-col cols="4">
                <v-select
                  label="장르*"
                  v-model="genre"
                  :items="option"
                  item-text="name"
                  item-value="value"
                  required
                ></v-select>
              </v-col>
                 <v-textarea
                    solo
                    v-model="content"
                    name="input-7-4"
                    label="내용"
                ></v-textarea>
            </v-row>
            <small>* 필수로 입력</small>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                :disabled="!valid"
                color="blue darken-1"
                text
                @click="createBoard()"
              >
                업로드
              </v-btn>
              <v-btn color="blue darken-1" text @click="close" >
                취소
              </v-btn>
            </v-card-actions>
          </v-form>
        </v-container>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>
<script>
import axios from 'axios'
export default {
  data () {
    return {
      // v-model 초기화
      dialog : false,
      valid:'',
      title:'',
      singer:'',
      genre:'',
      content:'',
      boardLink:'testLink',
      title_rule:[
          v => !!v || '노래제목은 필수 사항입니다.',
      ],
      singer_rule:[
          v => !!v || '가수 입력은 필수 사항입니다.',
      ],
      option: [
        { name: '발라드', value: 0 },
        { name: '힙합', value: 1 },
        { name: '댄스', value: 2 },
        { name: '기타', value: 3 }
      ],
     
    }
  }, props: {
    boardVideo: {
      type: String,
      required: true
    }
  },
  created() {
      this.$emit('create_routerview', 'Upload');
  },
  mounted(){
    
  },
  methods: {
    createBoard () {
      if(confirm('게시글을 작성하시겠습니까?')){
      axios.defaults.headers.common['Authorization']='Bearer '+localStorage.getItem('token')
      axios
        .post('http://api.maskstar.kr/board/createboard', {
          boardVideo: this.boardVideo,
          boardTitle: this.title,
          boardSinger: this.singer,
          boardGenre: this.genre,
          boardContent: this.content,
          boardLink: this.boardLink,
          userIdx: localStorage.getItem('userIdx'),
        })
        .then(response=>{
          if(response.status!=200){
            alert("게시글 작성을 실패하였습니다");
          }else{
            alert("게시글이 작성되었습니다!");
            this.$router.replace('/');
          } 
        })
        .catch(error => {
          alert("게시글 작성을 실패하였습니다")
          console.log(error)
          this.errored = true
        })
      }
    },
    close () {
      this.dialog = false
      this.$refs.form.reset()
      this.$refs.form.resetValidation()
    }
  }
}
</script>

<style lang="scss"></style>
