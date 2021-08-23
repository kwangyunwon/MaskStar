<template>
<div> 
  
  <div style="width:100%; height:50px; margin:10px;">
    <h3 id="header_"  style=" float:left">&nbsp;&nbsp;기타</h3>
    <v-select
          v-model="selected"
          :items="sortOption"
          solo
          outlined
          flat
          hide-details
          dense
          v-on:change="sort(selected)"
          light
          style="min-width:100px; max-width:100px; float:right; margin-top: 5px;margin-right: 30px;"></v-select>
  </div>
          
  
  <v-container fluid>
      <v-card class="transparent" flat infinite-wrapper>
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
              
              <infinite-loading
                @infinite="infiniteHandler"
                ref = "infiniteLoading"
                spinner="waveDots"
              >
              <span slot="no-more"> </span>
              <span slot="no-results"> </span>
              </infinite-loading> 
              </v-card>
    </v-container>
</div>
</template>

<script>
 
  import videoCard from '@/components/VideoCard'
  import axios from 'axios'
  import InfiniteLoading from 'vue-infinite-loading'
  export default {
  name: "Etc",
  
  data: () => ({    
    loading: true,
    board:{},
    boards: [],
    selected: '추천순',
    sortOption:['추천순','최신순'],
    start:0
  }),
    components: {      
      videoCard,
      InfiniteLoading
    },
    methods:{
      sort(sort){
        let s = 'RANK';
        if(sort=='최신순')
          s='IDX'
        this.start=0;
        this.loading= true;
        if(this.$refs.infiniteLoading){
             this.$refs.infiniteLoading.stateChanger.reset(); 
        }
        axios
        .get('http://api.maskstar.kr/board/selectgenre',{
           params:{
            'sort':s,
            'start':this.start,
            'genre':3
        }
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
        let s = 'RANK';
        if(this.selected=='최신순')
          s='IDX'
        axios
        .get('http://api.maskstar.kr/board/selectgenre', {
          params: {
            'sort':s,
            'start':this.start,
            'genre':3
          }
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
    },//methods

    mounted() {   
    this.sort("RANK")
    },//mounted

    created() {
      this.$emit('create_routerview', 'etc');
    }
  }
</script>
<style lang="scss">
#header_{
  margin-top: 10px;
}
 .v-select__selections input { 
  display: none;
  }

</style>
