<template>
<div>
  <div style="width:100%; height:50px; margin:10px;">
    <h3 id="header_"  style=" float:left" v-if="genre=='myboard'">&nbsp;&nbsp;{{keyword}}</h3>
    <h3 id="header_"  style=" float:left" v-else-if="genre=='BoardDetail'||genre=='home'">&nbsp;&nbsp;전체검색</h3>
    <h3 id="header_"  style=" float:left" v-else>&nbsp;&nbsp;{{genre}}</h3>
    <v-select
          v-model="selected"
          :items="sortOption"
          solo
          outlined
          flat
          hide-details
          dense
          v-on:change="sort()"
          style="min-width:100px; max-width:100px; float:right; margin-top: 5px;margin-right: 30px;"></v-select>
  </div>
  
  <v-container fluid infinite-wrapper>
      <v-card class="transparent" 
      flat
      :to="'/BoardDetail/'+board.boardIdx">
                <v-row>
                  <h5 v-if="boards.length<=0 && loading==false"  >검색 결과가 없습니다</h5>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                    lg="3"
                    v-for="i in loading ? 10 : boards.length"
                    :key="i"
                    class="mx-xs-auto"
                  >
                  
                    <v-skeleton-loader type="card-avatar" :loading="loading">
                      <video-card
                        :card="{ maxWidth: 350 }"
                        :board="boards[i-1]"
                      ></video-card>    
                    </v-skeleton-loader>
                  </v-col>
                </v-row>
              </v-card>
              <infinite-loading
                @infinite="infiniteHandler"
                ref = "infiniteLoading"
                spinner="waveDots"
              >
              <span slot="no-more"></span>
              <span slot="no-results"></span>
              </infinite-loading> 
    </v-container>
</div>
</template>

<script>
 
  import videoCard from '@/components/VideoCard'
  import axios from 'axios'
  import InfiniteLoading from 'vue-infinite-loading'

  export default {
  name: "SearchResult",
  
  data: () => ({
    keyword:'',
    searchOption : '',
    genre:'',
    loading: true,
    board:{},
    boards: [],
    selected: '추천순',
    sortOption:['추천순','최신순'],
    start:0
  }),
    components: {
      InfiniteLoading,
      videoCard
    },
    mounted() {     
    let searchUrl =this.checkSearchUrl(); 
    var param =this.checkParam();  
    axios
		.get('http://api.maskstar.kr/board/'+searchUrl,{
      params:param
    })
		.then(response=>(this.boards=response.data))
		.catch(error=>{
		console.log(error);
		this.errored=true;
		})
		.finally(()=>this.loading=false)
    this.start+=5;
    },//mounted

    methods:{
      checkSearchUrl(){
        var result='';
        if(this.searchOption=='유저')
          result='searchnickname';
        else if(this.searchOption=='노래') 
          result='searchtitle';
        else if(this.searchOption=='가수')
          result='searchsinger';
          return result;
      },
      checkParam(){
        var p={};
        let s = 'RANK';
        if(this.selected=='최신순')
          s='IDX'
        //전체 검색일 때
        if(this.genre=='home'&& this.genre=='myboard'){
          p = {
            'sort':s,
            'keyword': this.keyword,
            'start':this.start
          }
        }
        //장르 검색일 때 
        else{
          let g=null;
          switch(this.genre){
            case 'ballade' : g=0;break;
            case 'hiphop' : g=1;break;
            case 'dance' : g=2;break;
            case 'etc' : g=3;break;
            default: g=null;
          }
          p = {
            'genre':g,
            'sort':s,
            'keyword': this.keyword,
            'start':this.start
          }
        }
        //console.log(p);
        return p;
      },
      sort(){
        this.start=0;
        this.loading= true;
        let searchUrl =this.checkSearchUrl(); 
        var param =this.checkParam();        
        if(this.$refs.infiniteLoading){
             this.$refs.infiniteLoading.stateChanger.reset(); 
        }  
        axios
        .get('http://api.maskstar.kr/board/'+searchUrl,{
          params:param
         })
        .then(response=>(this.boards=response.data))
        .catch(error=>{
          console.log(error);
          this.errored=true;
        })
        .finally(()=>this.loading=false)
        this.start+=5;
      },//sort
      infiniteHandler ($state) {
        let searchUrl =this.checkSearchUrl(); 
        var param =this.checkParam();  
        axios
        .get('http://api.maskstar.kr/board/'+searchUrl,{
          params:param
         })
        .then(response => {
          setTimeout(() => {
            if (response.data.length) {
              this.boards = this.boards.concat(response.data)
              $state.loaded()
              this.start += 5
              if (this.boards.length / 5 == 0) {
                $state.complete()
              }
            } else {
              $state.complete()
            }
          }, 1000)
        })
        .catch(error => {
          console.log(error)
        })
      }
    },
    
    created() {
      this.keyword=this.$route.query.keyword;
      this.searchOption=this.$route.query.searchOption;
      this.genre = this.$route.query.genre;
    },
   
  }
</script>
<style lang="scss">
#header_{
  margin-top: 10px;
}
</style>
