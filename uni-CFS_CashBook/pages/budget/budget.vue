<template>
	<view>
		<view class="status_bar"></view>
		<view class="container">
			<uni-forms >
				<uni-forms-item label="吃" name="">
					<input v-model="eat" type="number" value="eat" placeholder="请输入吃的预算"/>
				</uni-forms-item>
				<uni-forms-item label="喝" name="">
					<input v-model="drink" type="number" value="" placeholder="请输入喝的预算" />
				</uni-forms-item>
				<uni-forms-item label="玩" name="">
					<input v-model="play" type="number" value="" placeholder="请输入玩的预算"/>
				</uni-forms-item>
				<uni-forms-item label="乐" name="">
					<input v-model="funny" type="number" value="" placeholder="请输入快乐的预算"/>
				</uni-forms-item>
				<uni-forms-item label="提升" name="">
					<input v-model="improve" type="number" value="" placeholder="提升"/>
				</uni-forms-item>
				<button type="default" v-model="judge" v-if="!judge" @click="doSubmit">提交</button>
				<button type="default" v-model="judge" v-if="judge" @click="doUpdate">更新</button>
			</uni-forms>
		</view>
		<tab-bar :current="2"></tab-bar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				id: -1,
				eat:0.00,
				drink:0.00,
				play:0.00,
				funny:0.00,
				improve:0.00,
				userId :0,
				judge: false,
			}
		},
		methods: {
			// 提交数据
			doSubmit(){
				uni.request({
					url: 'http://localhost:8080/budget',
					method: 'POST',
					data: {
						userId :this.userId,
						eat: this.eat,
						drink:this.drink,
						play:this.play,
						funny:this.funny,
						improve:this.improve
					},
					success: (res) => {
						console.log(res);
					}
				})
			},
			
			// 更新数据
			doUpdate(){
				uni.request({
					url: 'http://localhost:8080/budget',
					method: 'PUT',
					data: {
						userId :this.userId,
						eat: this.eat,
						drink:this.drink,
						play:this.play,
						funny:this.funny,
						improve:this.improve,
						id:this.id
					},
					success: (res) => {
						uni.showToast({
								title: res.data.msg,
								icon: 'none'
							})
					}
				})
			}
		},
		created(){
			uni.getStorage({
				key:'userId',
				success: (res) => {
					console.log("预算表",res.data);
					this.userId = res.data
					// 获取用户数据
					uni.request({
						url: 'http://localhost:8080/budget',
						method: 'GET',
						data: {
							userId: res.data
						},
						success: (res) => {
							this.judge = false;
							if(res.data){
								this.eat= res.data.eat,
								this.drink= res.data.drink,
								this.play= res.data.play,
								this.funny= res.data.funny,
								this.improve= res.data.improve,
								this.id = res.data.id;
								this.judge = true;
							}
						}
					})
				}
			})
		}
	}
</script>

<style>

.container{
	margin-left: 20rpx;
}
input{
	margin-top: 15rpx;
}
button{
	width: 500rpx;
	margin-bottom: 50rpx;
}
</style>
