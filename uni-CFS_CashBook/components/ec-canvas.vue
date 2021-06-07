<template>
	<view>
		<uni-ec-canvas class="uni-ec-canvas" :ec="ec"></uni-ec-canvas>
	</view>
</template>

<script>
	
	export default {
		data() {
			return {
				ec:{
					option:{
						// 标题
						title: {
							text: 'Echats 入门学习'
						},
						toltip:[],
						legend: {
							data: ['销量']
						},
						xAxis: {
							data: ['衬衫','羊毛衫','雪纺衫','裤子','高跟鞋','袜子']
						},
						yAxis:{},
						series: [{
							name: '销量',
							type: 'bar',
							data: [5,20,30,50,90,100]
						}]
					}
				}
			};
		}
	}
</script>

<style>

</style>
