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
						imageHover: '../static/homeHover.png',
						url: '../pages/home/home'
					},
					{
						text: '收入支出',
						image: '../static/income.png',
						imageHover: '../static/incomeHover.png',
						url: '../pages/inandout/income'
					},
					{
						text: '预算管理',
						image: '../static/budget.png',
						imageHover: '../static/budgetHover.png'
					},
					{
						text: '投资提现',
						image: '../static/withdrawal.png',
						imageHover: '../static/withdrawalHover.png'
					},
					{
						text: '查询统计',
						image: '../static/statistics.png',
						imageHover: '../static/statisticsHover.png'
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
							url: "/pages/home/home",
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
							url: "/pages/inandout/income/income",
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
							url: "/pages/budget/budget",
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
							url: "/pages/remind/remind",
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
							url: "/pages/calculate/calculate",
							success: (res) => {
								console.log(res)
							},
							fail: (err) => {
								console.log(err)
							}
						})
						break
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
