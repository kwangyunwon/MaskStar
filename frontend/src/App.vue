<template>
  <v-app>
    <v-main>
      <Navbar :componentName="componentName"/>
      <router-view @create_routerview="getComponentName" :key="$route.fullPath"/>
    </v-main>
  </v-app>
</template>

<script>
import Navbar from './components/Navbar.vue'
export default {
  
  name: 'App',
  components: {
      Navbar
    },
  data: () => ({
    componentName:'',
    now:"00:00"
  }),
  methods:{
    getComponentName:function(name){      
      this.componentName=name;
      console.log('getComponentName  '+this.componentName)
    }
  },//methods
  mounted(){
    if(localStorage.getItem('id')!=null){

      const date = new Date();
      var nDate = 0;
      if(date.getHours() < 9){
        nDate = (15-date.getHours())*3600+date.getMinutes()*60 
      } else{
        nDate = (date.getHours()-9)*3600+date.getMinutes()*60
      }
      
      const temp = localStorage.getItem('date').split(":")
      const tDate = (temp[0]*3600)+(temp[1]*60)
 
      if(nDate>tDate){
        localStorage.clear()
        location.reload()  
      }    
    }
  }
  
};//default
</script>
