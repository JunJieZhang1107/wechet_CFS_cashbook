<template>
	<view class="block">
		<block v-for="(item,index) in tabList" :key="index">
			<view class="tabbar" :class="currentTabIndex == index ? 'on' : ''" @click="switchTab(index)">
				<view class="tabbar-image">
					<image :src="item.imageHover" mode=""
					v-if="currentTabIndex == index"
					class="images"
					></image>
					<image :src="item.image" mode=""
					class="images"
					v-else
					></image>
				</view>
				<text class="tabbar-text"
				:style="[currentTabIndex == index ? {'color': tintColor} : {'color': color}]"
				>{{ item.text }}</text>
			</view>
		</block>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				tabList: [
					{
						text: '首页',
						image: '../static/home.png',
						imageHover: '../static/api.png',
						url: '../pages/home/home'
					},
					{
						text: '收入支出管理',
						image: '../static/home.png',
						imageHover: '../static/api.png',
						url: '../pages/home/home'
					},
					{
						text: '预算管理',
						image: '../static/home.png',
						imageHover: '../static/api.png'
					},
					{
						text: '投资提现模块',
						image: '../static/home.png',
						imageHover: '../static/api.png'
					},
					{
						text: '查询统计',
						image: '../static/home.png',
						imageHover: '../static/api.png'
					}
				],
				currentTabIndex: this.current
			}
		},
		props: {
			current: { type: [Number, String], default: 0 },
			backgroundColor: { type: String, default: '#fbfbfb' },
			color: { type: String, default: '#999' },
			tintColor: { type: String, default: '#42b983' }
		},
		methods: {
			switchTab(index){
				console.log(index)
				this.currentTabIndex = index
				this.$emit('click', index)
				switch(index){
					case 0:
						uni.reLaunch({
							url: "../home/home",
							success: (res) => {
								console.log(res)
							},
							fail: (err) => {
								console.log(err)
							}
						})
						break
					case 1:
						uni.reLaunch({
							url: "../inandout/inandout",
							success: (res) => {
								console.log(res)
							},
							fail: (err) => {
								console.log(err)
							}
						})
						break
					case 2:
						uni.reLaunch({
							url: "../budget/budget",
							success: (res) => {
								console.log(res)
							},
							fail: (err) => {
								console.log(err)
							}
						})
						break
					case 3:
						uni.reLaunch({
							url: "../remind/remind",
							success: (res) => {
								console.log(res)
							},
							fail: (err) => {
								console.log(err)
							}
						})
						break
					case 4:
						uni.reLaunch({
							url: "../calculate/calculate",
							success: (res) => {
								console.log(res)
							},
							fail: (err) => {
								console.log(err)
							}
						})
					default:
						uni.reLaunch({
							url: "../home/home",
							success: (res) => {
								console.log(res)
							},
							fail: (err) => {
								console.log(err)
							}
						})
				}
			}
		},
	}
</script>

<style>
.block{
	display: flex;
	border-top: 1px solid rgb(247,247,247);
	height: 120rpx;
	position: absolute;
	bottom: 0;
	width: 100%;
}
.tabbar{
	flex-direction: column;
	align-items: flex-start;
	width: 20%;
	text-align: center;
}
.tabbar-image{
	flex-direction: column;
	align-items: flex-end;
}
.images{
	width: 50rpx;
	height: 50rpx;
	position: relative;
	top: 35rpx;
	left: 17%;
	transform: translate(-50%,-50%);
}
.tabbar-text{
	font-size: 12px;
}
</style>
