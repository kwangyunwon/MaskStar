<template>

  <div>
    <v-container fluid>
      <v-row>
        <v-col cols="12" class="mx-auto">
          <v-row align="center" justify="center">
            <v-col cols="12" sm="12" md="8" lg="8">
              <v-skeleton-loader
                type="card-avatar, article, actions"
                :loading="videoLoading"
                tile
                large
              >
                <v-responsive>
                  <!-- <iframe
                    width="100%"
                    height="400"
                    :src="this.videosrc"
                    frameborder="0"
                    allow="encrypted-media"
                    allowfullscreen
                  ></iframe> -->
                  <video controls>
                    <source :src="this.videosrc" type="video/mp4">
                  </video>
                </v-responsive>
                <v-card flat class="transparent">
                  <v-card-title class="pl-0 pb-0">
                    [{{ board.boardTitle }}-{{
                      board.boardSinger
                    }}]</v-card-title
                  >
                  <div class="d-flex flex-wrap justify-space-between" id="btns">
                    <v-card-subtitle
                      class="pl-0 pt-0 pb-0 subtitle-1"
                      style="line-height: 2.4em;"
                      v-if="!videoLoading"
    
                    >
                    <div v-if="board.updateDate==null"> 
                      <b @click="searchMyBoard" style="cursor:pointer">{{board.authVO.nickname}}</b> &nbsp; 조회수 {{ board.boardCount }} &nbsp; {{ board.uploadDate }}
                    </div>
                    <div v-else>
                      <b @click="searchMyBoard" style="cursor:pointer">{{board.authVO.nickname}}</b> &nbsp; 조회수 {{ board.boardCount }} &nbsp; {{ board.updateDate }}(수정됨)
                    </div>
                    </v-card-subtitle>
                    <v-card-actions class="pt-0 pl-0 grey--text">
                    <div v-if="user.userIdx==board.userIdx">
                        <v-btn 
                        small
                        text 
                          >
                          <UpdateBoard :board="board"/>
                        </v-btn>
                        <v-btn 
                        small 
                        text 
                        color="error"
                        @click="deleteBoard()"
                          >
                          삭제
                          </v-btn
                        >
                      </div>
                      
                    </v-card-actions>
                  </div>
                </v-card>
                <v-divider></v-divider>
                <v-row class="justify-space-between">
                  <v-col  cols="12" md="12" >
                    <br>
                    <p style="height:50px">
                      {{ board.boardContent }}
                    </p>
                  </v-col>
                  <v-col cols="12" align="center">
                    <div v-if="isBoardLike == 0">
                        <v-btn text @click="addLike(), (isBoardLike = 1)" :disabled="user.userIdx==null"
                          outlined x-large><v-icon class="pr-2">mdi-thumb-up</v-icon>
                          {{ board.boardLike }}</v-btn
                        >
                      </div>
                      <div v-if="isBoardLike == 1">
                        <v-btn text @click="addLike(), (isBoardLike = 0)" outlined x-large
                          ><v-icon class="pr-2" color="blue"
                            >mdi-thumb-up</v-icon
                          >
                          {{ board.boardLike }}</v-btn
                        >
                      </div>
                  </v-col>
                  <v-col cols="12"> 
                    <p class="mb-0">댓글 {{ commentCnt }} </p>
                    <v-card class="transparent" flat>
                      <v-list-item three-line class="pl-0">
                        <v-list-item-content class="align-self-auto">
                          <v-text-field
                            v-if="user.userIdx==null"
                            v-model="commentContent"
                            placeholder="로그인 후 이용해주세요."
                            @click="showCommentBtns = true" :disabled="user.userIdx==null"
                          >
                          </v-text-field>
                          <v-text-field
                            v-else
                            v-model="commentContent"
                            placeholder="댓글 작성하기..."
                            @click="showCommentBtns = true"
                          >
                          </v-text-field>
                          <div
                            v-if="showCommentBtns"
                            class="d-inline-block text-right"
                          >
                            <v-btn
                              class="blue darken-3 white--text"
                              depressed
                              tile
                              small
                              :disabled="commentContent === ''"
                              @click="insertComment"
                              >등록</v-btn
                            >
                            <v-btn
                              text
                              small
                              @click="showCommentBtns = !showCommentBtns"
                              >취소</v-btn
                            >
                            
                          </div>
                        </v-list-item-content>
                      </v-list-item>
                    </v-card>
                    <!-- 댓글 -->
                    <v-card
                      class="transparent"
                      flat
                      v-for="c in comments"
                      :key="c.commentIdx"
                    >
                      <v-list-item three-line class="pl-0 mt-2">
                        <v-list-item-content>
                          <div>
                            <v-list-item-title>
                              {{ c.authVO.nickname }} 
                              &nbsp;&nbsp;&nbsp;
                              <span 
                              style="font-size:5px"
                              v-if="c.updateDate==null && c.commentDelete==0"
                              >{{ c.uploadDate}}</span>
                              <span 
                              style="font-size:5px"
                              v-if="c.updateDate!=null && c.commentDelete==0"
                              >{{ c.updateDate}}(수정됨)</span>
                            </v-list-item-title>
                          </div>
                          <v-list-item-subtitle>
                            <br>
                            {{ c.commentContent }}
                            <br>
                          </v-list-item-subtitle>
                          <div v-if="c.commentDelete == 0">
                            <br>
                             <v-btn
                              depressed
                              x-small
                              :ripple="false"
                              :disabled="user.userIdx==null"
                              @click.stop="
                                showReply(`${'reply' + c.commentIdx}`)
                              "
                              >답글</v-btn
                            >
                            {{" "}}
                            <v-btn
                              x-small
                              depressed
                              :ripple="false"
                              v-if="c.userIdx == user.userIdx"
                              @click.stop="
                                showReply(`${'update' + c.commentIdx}`)
                              "
                              >수정</v-btn
                            >
                            {{" "}}
                            <v-btn
                              v-if="c.userIdx == user.userIdx"
                              x-small
                              depressed
                              @click="deleteComment(c.commentIdx)"
                              >삭제</v-btn
                            >
                             <v-btn
                                  text
                                  @click="addCommentLike(c.commentIdx)" :disabled="user.userIdx==null"
                                  small
                                >
                                  <v-icon class="pr-2" small color="blue" v-if="c.commentDelete == 0 && likeList.includes(c.commentIdx)" >mdi-thumb-up</v-icon>
                                  <v-icon class="pr-2" small v-else>mdi-thumb-up</v-icon>
                                  {{ c.commentLike }}
                                </v-btn>
                          </div>
                          <div
                            class="d-none"
                            :ref="`${'reply' + c.commentIdx}`"
                          >
                            <v-list-item three-line class="pl-0">
                              <v-list-item-content
                                class="align-self-auto mt-0 pt-0"
                              >
                                <v-text-field
                                  v-model="subCommentFrm.subCommentContent"
                                  placeholder="댓글 작성하기..."
                                >
                                </v-text-field>
                                <div class="d-inline-block text-right">
                                  <v-btn
                                    depressed
                                    small
                                    :disabled="
                                      subCommentFrm.subCommentContent === ''
                                    "
                                    @click.stop="
                                      showReply(`${'reply' + c.commentIdx}`)
                                    "
                                    @click="insertSubComment(c.commentIdx)"
                                    >등록</v-btn
                                  >
                                  <v-btn
                                    text
                                    small
                                    @click.stop="
                                      showReply(`${'reply' + c.commentIdx}`)
                                    "
                                    >취소</v-btn
                                  >
                                </div>
                              </v-list-item-content>
                            </v-list-item>
                          </div>
                          
                          <!-- 댓글수정 -->
                          <div
                            class="d-none"
                            :ref="`${'update' + c.commentIdx}`"
                          >
                            <v-list-item three-line class="pl-0">
                              <v-list-item-content
                                class="align-self-auto mt-0 pt-0"
                              >
                                <v-text-field
                                  v-model="updateCmtFrm.commentContent"
                                  placeholder="댓글 수정하기"
                                >
                                </v-text-field>
                                <div class="d-inline-block text-right">
                                  <v-btn
                                    depressed
                                    small
                                    :disabled="
                                      updateCmtFrm.commentContent === ''
                                    "
                                    @click.stop="
                                      showReply(`${'update' + c.commentIdx}`)
                                    "
                                    @click="updateComment(c.commentIdx)"
                                    >수정</v-btn
                                  >
                                  <v-btn
                                    text
                                    small
                                    @click.stop="
                                      showReply(`${'update' + c.commentIdx}`)
                                    "
                                    >취소</v-btn
                                  >
                                </div>
                              </v-list-item-content>
                            </v-list-item>
                          </div>

                          <!-- 대댓글 -->
                          <v-card
                            class="transparent"
                            flat
                            v-for="s in subComments"
                            :key="s.subCommentIdx"
                          >
                            <div v-if="c.commentIdx == s.commentIdx">
                             
                              <v-list-item three-line class="pl-0 mt-2">
                                 <v-icon large>mdi-subdirectory-arrow-right</v-icon>
                                 &nbsp;  &nbsp;
                                <v-list-item-content>
                                  <v-list-item-title>
                                    {{ s.authVO.nickname }}
                                    &nbsp;&nbsp;&nbsp;
                                    <span v-if="s.subCommentDelete == 0">
                                       <span style="font-size:5px"
                                       v-if="s.updateDate==null">{{ s.uploadDate}}</span>
                                       <span style="font-size:5px" v-else>{{ s.updateDate}}(수정됨)</span>
                                    </span>
                                  </v-list-item-title>
                                  <v-list-item-subtitle>
                                    {{ s.subCommentContent }}
                                  </v-list-item-subtitle>
                                  <div v-if="s.subCommentDelete == 0 && s.userIdx == user.userIdx">
                                    <v-btn
                                      depressed
                                      x-small
                                      :ripple="false"
                                      @click.stop="
                                        showReply(
                                          `${'subUpdate' + s.subCommentIdx}`
                                        )
                                      "
                                      >수정</v-btn
                                    >
                                    {{" "}}
                                    <v-btn
                                      depressed
                                      x-small
                                      @click="deleteSubComment(s.subCommentIdx)"
                                      >삭제</v-btn
                                    >
                                  </div>
                                  <div
                                    class="d-none"
                                    :ref="`${'subUpdate' + s.subCommentIdx}`"
                                  >
                                    <v-list-item three-line class="pl-0">
                                      <v-list-item-content
                                        class="align-self-auto mt-0 pt-0"
                                      >
                                        <v-text-field
                                          v-model="
                                            updateSubCmtFrm.subCommentContent
                                          "
                                          placeholder="댓글 수정하기"
                                        >
                                        </v-text-field>
                                        <div class="d-inline-block text-right">
                                          <v-btn
                                            class="blue darken-3 white--text"
                                            depressed
                                            tile
                                            small
                                            :disabled="
                                              updateSubCmtFrm.subCommentContent ===
                                                ''
                                            "
                                            @click.stop="
                                              showReply(
                                                `${'subUpdate' +
                                                  s.subCommentIdx}`
                                              )
                                            "
                                            @click="
                                              updateSubComment(s.subCommentIdx)
                                            "
                                            >수정</v-btn
                                          >
                                          <v-btn
                                            text
                                            small
                                            @click.stop="
                                              showReply(
                                                `${'subUpdate' +
                                                  s.subCommentIdx}`
                                              )
                                            "
                                            >취소</v-btn
                                          >

                                        </div>
                                      </v-list-item-content>
                                    </v-list-item>
                                  </div>
                                </v-list-item-content>
                              </v-list-item>
                            </div>
                          </v-card>
                        </v-list-item-content>
                      </v-list-item>
                      <v-divider></v-divider>
                    </v-card>
                    <infinite-loading
                      @infinite="infiniteHandler"
                      no-results=""
                      spinner="waveDots"
                    >
                    <span slot="no-more"></span>
                    <span slot="no-results"></span>
                    </infinite-loading>
                  </v-col>
                </v-row>
              </v-skeleton-loader>
            </v-col>

          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import InfiniteLoading from 'vue-infinite-loading'
import axios from 'axios'
import UpdateBoard from './UpdateBoard.vue'
//import VideoCard from './VideoCard.vue'

let users = [
  {
    userIdx: localStorage.getItem("userIdx"),
    userNickname: localStorage.getItem("nickname"),
  }
]
let videos = [
  {
    id: 1,
    title: 'Title',
    thumbnail:
      'https://i.ytimg.com/vi/jn9mHzXJIV0/hqdefault.jpg?sqp=-oaymwEZCNACELwBSFXyq4qpAwsIARUAAIhCGAFwAQ==&rs=AOn4CLAvJvk4k_UNB9nst4pFP-txM1TLZA',
    youtubeURL: 'https://www.youtube.com/embed/jn9mHzXJIV0',
    creator: 'Marques Brownlee',
    likes: 0,
    dislikes: 0,
    createdAt: '2 years ago',
    subscribers: '100k',
    views: 4526,
    description:
      'Lorem, ipsum dolor sit amet consectetur adipisicing elit. Culpa vel inventore voluptatum reiciendis delectus quibusdam incidunt consequuntur, nostrum aperiam, natus quidem qui corrupti reprehenderit quaerat neque voluptatibus? Ullam, maiores temporibus! Lorem, ipsum dolor sit amet consectetur adipisicing elit. Culpa vel inventore voluptatum reiciendis delectus, Lorem, ipsum dolor sit amet consectetur adipisicing elit. Culpa vel inventore voluptatum reiciendis delectus'
  },
  
]

export default {

  name: 'VideoPlayer',
  components: {
    InfiniteLoading,
    UpdateBoard,
    //VideoCard,
    
  },
  data () {
    return {
      videos,
      activeVideo: videos[0],
      loading: true,
      videoLoading: true,
      truncate: true,
      showCommentBtns: false,
      repliesInput: {},
      user: users[0],
      board: {},
      comments: [],
      subComments: [],
      isBoardLike: 0,
      likeList: [],
      //commentFrm: {},
      commentContent: '',
      boardUploader :'',
      updateCmtFrm: {},
      updateSubCmtFrm: {},
      subCommentFrm: {},
      boardLike: {},
      commentLike: {},
      limit: 5,
      hasMore: true,
      commentCnt:0,
      boardIdx: 0,
      videosrc : 'https://s3.us-east-2.amazonaws.com/maskstar.kr/media/'
    }
  }, 
  methods: {
    chooseVideo (video) {
      //SET VIDEO AS ACTIVE VIDEO
      this.activeVideo = video
      //INCREASE THE VIDEOS VIEWS BY 1
      video.views += 1
    },

    infiniteHandler ($state) {
      
      axios
        .get('http://api.maskstar.kr/comment/selectallcomments', {
          params: {
            boardIdx: this.boardIdx,
            limit: this.limit
          }
        })
        .then(response => {
          setTimeout(() => {
            if (response.data.length) {
              this.comments = this.comments.concat(response.data)
              $state.loaded()
              this.limit += 5
              if (this.comments.length / 5 == 0) {
                $state.complete()
                this.hasMore = false
              }
            } else {
              $state.complete()
              this.hasMore = false
            }
          }, 1000)
        })
        .catch(error => {
          console.log(error)
          this.errored = true
        })
    },

    show (event) {
      if (event.target.innerText === 'SHOW MORE') {
        this.truncate = false
        event.target.innerText = 'SHOW LESS'
      } else {
        this.truncate = true
        event.target.innerText = 'SHOW MORE'
      }
    },
    truncateText (string = '', num) {
      if (string.length <= num) {
        return string
      }
      return string.slice(0, num)
    },
    showReply (id) {
      this.$refs[id][0].classList.toggle('d-none')
    },
    hideReply (id) {
      this.$refs[`form${id}`][0].reset()
      this.$refs['reply' + id][0].classList.toggle('d-none')
    },
    insertComment () {
      if(confirm('댓글을 작성하시겠습니까?')){
      axios
        .post('http://api.maskstar.kr/comment/insertcomment', {
          userIdx: this.user.userIdx,
          boardIdx: this.boardIdx,
          commentContent: this.commentContent
        })
        .then(
          () => (this.showCommentBtns = false),
          (this.commentContent = ''),
          this.readComment()
        )
        .catch(error => {
          console.log(error)
          this.errored = true
        })
      }
    },
    deleteBoard () {
      if(confirm('글을 삭제하시겠습니까?')){
      axios
        .delete('http://api.maskstar.kr/board/deleteboard/' + this.boardIdx)
        .then(() => alert("글이 삭제되었습니다."), this.$router.replace('/'),location.reload())
        .catch(error => {
          console.log(error)
          this.errored = true
        })
      }
    },
    
    deleteComment (commentIdx) {
      if(confirm('댓글을 삭제하시겠습니까?')){
      axios
        .put('http://api.maskstar.kr/comment/deletecomment/' + commentIdx)
        .then(() => this.readComment())
        .catch(error => {
          console.log(error)
          this.errored = true
        })
      }
    },
    deleteSubComment (subCommentIdx) {
      if(confirm('댓글을 삭제하시겠습니까?')){
      axios
        .put('http://api.maskstar.kr/subcomment/deletesubcomment/' + subCommentIdx)
        .then(() => this.readComment())
        .catch(error => {
          console.log(error)
          this.errored = true
        })
      }
    },

    insertSubComment (commentIdx) {
      if(confirm('댓글을 작성하시겠습니까?')){
      this.subCommentFrm.userIdx = this.user.userIdx
      this.subCommentFrm.boardIdx = this.boardIdx
      this.subCommentFrm.commentIdx = commentIdx
      axios
        .post('http://api.maskstar.kr/subcomment/insertsubcomment', this.subCommentFrm)
        .then(() => this.readComment())
        .catch(error => {
          console.log(error)
          this.errored = true
        })
      }
    },
    updateComment (commentIdx) {
      if(confirm('댓글을 수정하시겠습니까?')){
      this.updateCmtFrm.commentIdx = commentIdx
      axios
        .put('http://api.maskstar.kr/comment/updatecomment', this.updateCmtFrm)
        .then(() => this.readComment())
        .catch(error => {
          console.log(error)
          this.errored = true
        })
      }
    },
    updateSubComment (subCommentIdx) {
      if(confirm('댓글을 수정하시겠습니까?')){
      this.updateSubCmtFrm.subCommentIdx = subCommentIdx
      axios
        .put('http://api.maskstar.kr/subcomment/updatesubcomment', this.updateSubCmtFrm)
        .then(() => this.readComment())
        .catch(error => {
          console.log(error)
          this.errored = true
        })
      }
    },

    addLike () {
      this.boardLike.userIdx = this.user.userIdx
      this.boardLike.boardIdx = this.boardIdx
      axios
        .post('http://api.maskstar.kr/board/addlike', this.boardLike)
        .then(() => this.readBoard())
        .catch(error => {
          console.log(error)
          this.errored = true
        })
    },

    addCommentLike (commentIdx) {
      this.commentLike.userIdx = this.user.userIdx
      this.commentLike.commentIdx = commentIdx
      this.commentLike.boardIdx = this.boardIdx
      axios
        .post('http://api.maskstar.kr/comment/insertcommentlike', this.commentLike)
        .then(()=> {
          this.readComment();
        })
        
        .catch(error => {
          console.log(error)
          this.errored = true
        })
    },
    readComment () {
      if (!this.hasMore) {
        this.hasMore = true
        this.$router.go()
      }
     
      axios
        .get('http://api.maskstar.kr/comment/selectallcomments', {
          params: {
            boardIdx: this.boardIdx,
            limit: 0
          }
        })
        .then(response => (this.comments = response.data))
        .catch(error => {
          console.log(error)
          this.errored = true
        })
     axios
        .get('http://api.maskstar.kr/comment/commentcnt/'+this.boardIdx)
        .then(response => (this.commentCnt = response.data))
        .catch(error => {
          console.log(error)
          this.errored = true
        })

      axios
        .get('http://api.maskstar.kr/subcomment/selectallsubcomments/' + this.boardIdx)
        .then(response => (this.subComments = response.data))
        .catch(error => {
          console.log(error)
          this.errored = true
        })
      if(localStorage.getItem('id')!=null){
      axios
        .get('http://api.maskstar.kr/comment/commentlikelist', {
          params: {
            boardIdx: this.boardIdx,
            userIdx: localStorage.getItem('userIdx')
          }
        })
        .then(response => (this.likeList = response.data))
        .catch(error => {
          console.log(error)
          this.errored = true
        })
      }
      this.limit = 5
    },
    readBoard () {
      axios
        .get('http://api.maskstar.kr/board/selectboardbyid/' + this.boardIdx)
        .then(response => {
          this.board = response.data,
          this.videosrc += response.data['boardVideo']
          }
        )
        .catch(error => {
          console.log(error)
          this.errored = true
        })
        .finally(() => {  
      
          this.loading = false
          this.videoLoading = false
        })
    },
    checkBoardLike () {
      if(localStorage.getItem('id')!=null){
        axios
          .get('http://api.maskstar.kr/board/checklike', {
            params: {
              userIdx: this.user.userIdx,
              boardIdx: this.boardIdx
            }
          })
          .then(response => (this.isBoardLike = response.data))
          .catch(error => {
            console.log(error)
            this.errored = true
          })
      }
    },
    searchMyBoard() {            
      this.$router.push({
        name: "SearchResult",
        query: { 
          genre:'myboard',
          keyword: this.board.authVO.nickname ,
          searchOption : '유저',
          t: new Date().getTime()
          }
      });
    }//searchMyBoard

  },
  created() {
      this.$emit('create_routerview', 'BoardDetail');
    },
  mounted () {
    if(localStorage.getItem('token')!=null){
      axios.defaults.headers.common['Authorization']='Bearer '+localStorage.getItem('token')
    }
    this.boardIdx = this.$route.params.boardIdx    
    this.readBoard()
    this.readComment()
    this.checkBoardLike() 
    
  }  
}


		

</script>

<style scoped></style>
