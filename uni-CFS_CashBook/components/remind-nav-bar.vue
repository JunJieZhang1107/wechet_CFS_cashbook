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
					{ title: '投资'},
					{ title: '负债提醒'},
					{ title: '资金回笼提醒' }
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
							url: '/pages/remind/remind'
						})
						break
					case 1:
						uni.reLaunch({
							url: '/pages/remind/investment/debtReminder/debtReminder'
						})
						break
					case 2:
						uni.reLaunch({
							url: '/pages/remind/investment/returnOfFunds/loanAmount/loanAmount'
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
