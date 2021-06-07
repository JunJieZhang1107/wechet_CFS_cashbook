<template>
	<view>
		<view class="nav-bar">
			<view class="nav-bar-list" 
			v-for="(item,index) in titleList" 
			:key="index"
			@getIndex="getIndex"
			@click="clickTitle(index)"
			:style="[currentTabIndex == index ? {'background':'#0000FF'} : {'background':''}]">
				<view>
					{{ item.title }}
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				titleList:[
					{ title: '收入'},
					{ title: '支出'},
					{ title: '负债'},
					{ title: '外借'},
					{ title: '记录'}
				],
				currentTabIndex: this.current
			};
		},
		props:{
			current: { type:[Number,String], default: 0 },
			title: { type:String, default: ''}
		},
		methods:{
			clickTitle(index){
				this.currentTabIndex = index
				// console.log(this.TabIndex)
				this.$emit('click',index)
				switch(index){
					case 0:
						uni.reLaunch({
							url: '/pages/inandout/income/income'
						})
						break
					case 1:
						uni.reLaunch({
							url: '/pages/inandout/expenditure/expenditure'
						})
						break
					case 2:
						uni.reLaunch({
							url: '/pages/inandout/liabilities/liabilities'
						})
						break
					case 3:
						uni.reLaunch({
							url: '/pages/inandout/lend/lend'
						})
						break
					case 4:
						uni.reLaunch({
							url: '/pages/inandout/list/list'
						})
						break
				}
			}
		}
	}
</script>

<style>
.nav-bar{
	width: 100%;
	height: 44px;
	display: flex;
	border-bottom: 1px solid #AAAAAA;
}
.nav-bar-list{
	flex-direction: column;
	flex-grow: 1;
	text-align: center;
	line-height: 44px;
}
.bgColor{
	background-color: #0000FF;
}
.bgRed{
	background-color: red;
}
</style>
