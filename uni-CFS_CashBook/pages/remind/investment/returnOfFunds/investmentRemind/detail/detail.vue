<template>
	<view>
		<view class="status_bar"></view>
		<remind-nav-bar :current="2"></remind-nav-bar>
		<view style="margin-left: 90rpx;margin-top: 20rpx;">
			<uni-forms >
				<uni-forms-item label="资金账户" name="">
					<text>{{account.accountName}}</text>
				</uni-forms-item>
				<uni-forms-item label="产品名" name="">
					<text>{{invest.productName}}</text>
				</uni-forms-item>
				<uni-forms-item label="投资种类" name="">
					<text>{{invest.investType}}</text>
				</uni-forms-item>
				<uni-forms-item label="投资金额" name="">
					<text>{{invest.investMoney}}</text>
				</uni-forms-item>
				<uni-forms-item label="回款时间" name="">
					<text>{{invest.backTime}}</text>
				</uni-forms-item>
				<uni-forms-item label="回款金额" name="">
					<input v-model="invest.backMoney" type="number" value="" placeholder="回款金额"/>
				</uni-forms-item>
			</uni-forms>
		</view>
		<button type="default" v-if="judge"  @click="doSubmit">提交</button>
		<tab-bar :current="3"></tab-bar>
	</view>
</template>

<script>
	export default {
		onLoad:function(option){
			this.account.accountId = option.accountId;
			this.invest.investId = option.investId;
			// 请求后端数据
			uni.request({
				url: 'http://localhost:8080/investDetail/'+this.account.accountId+"/"+this.invest.investId,
				method: 'GET',
				success: (res) => {
					console.log(res);
					this.account = res.data.account;
					this.invest = res.data.invest;
					var now = new Date();
					var nowTime = now.getFullYear() +"-"+ ((now.getMonth() + 1) < 10 ? "0" : "") + (now.getMonth() + 1) + "-"+(now.getDate() < 10 ? "0" : "") + now.getDate() ;
					if(this.invest.investType==3 && nowTime >= this.invest.backTime){
						this.judge = true;
					}else if(this.invest.investType == 3){
						uni.showToast({
							title: "该产品为定期产品，无法提前赎回",
							icon: "none"
						})
						this.judge = false;
					}else{
						this.judge = true;
					}
					switch(this.invest.investType){
						case 1:  this.invest.investType = "基金"; break;
						case 2:  this.invest.investType = "股票"; break;
						case 3:  this.invest.investType = "定期";break;
						default: break;
					}
				}
			})
		},   
		data() {
			return {
				account:{},
				invest:{},
				judge:false
			}
		},
		methods: {
			doSubmit(){
				if(this.invest.backMoney == ""){
					uni.showToast({
						title: "回款资金不可为空",
						icon: "none"
					})
				}else{
					uni.request({
						url: 'http://localhost:8080/invest',
						method: 'PUT',
						data:{
							accountId:this.invest.accountId,
							backMoney:this.invest.backMoney,
							investId:this.invest.investId,
							investMoney:this.invest.investMoney,
						},
						success: (res) => {
							if(res.data.msg == "更新数据成功")
							{
								uni.showToast({
									title:"数据上传成功",
									icon:"success"
								})
							}else{
								uni.showToast({
									title: "数据上传失败",
									icon: "none"
								})
							}
						}
					})
				}
				
			}
		}
	}
</script>

<style>

</style>
