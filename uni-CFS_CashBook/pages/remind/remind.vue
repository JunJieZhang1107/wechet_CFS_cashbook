<template>
	<view>
		<view class="status_bar"></view>
		<remind-nav-bar :current="0"></remind-nav-bar>
		<view class="remind">
			<uni-forms>
				<uni-forms-item label="账户名" name="">
					<picker mode="selector" :range="selectAccount" :value="accountIndex" @change="pickerSelect">
						<view class="pickerView">{{ selectAccount[accountIndex] }}</view>
					</picker>
				</uni-forms-item>
				<uni-forms-item label="产品名" name="">
					<input type="text" v-model = "this.productName" value="" placeholder="请输入产品名"/>
				</uni-forms-item>
				<uni-forms-item label="投资类型" name="">
					<picker mode="selector" :range="type" :value="typeIndex" @change="pickerType">
						<view class="pickerView">{{ type[typeIndex] }}</view>
					</picker>
				</uni-forms-item>
				<uni-forms-item label="金额" name="">
					<input type="number"v-model ="money" value="" />
				</uni-forms-item>
				<uni-forms-item label="回款时间" name="">
					<picker mode="date" :value="date" :start="date" @change="pickDate">
						<view>{{ date }}</view>
					</picker>
				</uni-forms-item>
				<button @click="submitData">提交投资数据</button>
			</uni-forms>
		</view>
		<tab-bar :current="3"></tab-bar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				selectAccount: [],
				selectAccountOtherMsg:[],
				type: ['基金','股票','定期'],
				typeNum:[1,2,3],
				accountIndex: 0, // 账号的index
				typeIndex: 0,  // 类型的index
				money: 0 ,//投资金额
				productName:"",
				date:'请选择日期',
			}
		},
		methods: {
			submitData(){
				var freeMoney = this.selectAccountOtherMsg[this.accountIndex].money;
				var investType = this.typeNum[this.typeIndex];
				var investMoney = this.money;
				var accountId = this.selectAccountOtherMsg[this.accountIndex].accountId;
				var productName = this.productName;
				var backTime = this.date;
				if(this.selectAccount.length == 0){
					uni.showToast({
						title: "请创建账户",
						icon: "none",
					})
					return;
				}
				if(productName == ""){
					uni.showToast({
						title: "请输入产品名",
						icon: "none",
					})
					return;
				}
				if(investMoney == 0 ){
					uni.showToast({
						title: "请输入投资金额",
						icon: "none",
					})
					return;
				}
				if(backTime == "请选择日期")
				{
					uni.showToast({
						title: "请选择日期",
						icon: "none",
					})
					return;
				}
				
				if(freeMoney >= investMoney){ // 金额充足，提交数据
					uni.request({
						url: 'http://localhost:8080/invest',
						method: 'POST',
						data: {
							accountId: accountId,
							productName: productName,
							investType:investType,
							investMoney: investMoney,
							backTime: backTime,
						},
						success: (res) => {
							uni.showToast({
								title: res.data.msg,
								icon: "none",
							})
							if(res.data.msg == "保存成功"){
								this.accountIndex= 0, // 账号的index
								this.typeIndex= 0,  // 类型的index
								this.money=0 ,//投资金额
								this.productName="",
								this.date='请选择日期'
							}
						},
					})
				}else{
					uni.showToast({
						title: "金额不足",
						icon: "none",
					})
				}
			},
			pickDate(e) {
				this.date = e.target.value
			},
			pickerSelect(e){
				this.accountIndex = e.target.value
			},
			pickerType(e){
				this.typeIndex = e.target.value
			},
			getAllAccount() {
				uni.getStorage({
					key: 'userId',
					success: (res) => {
						this.userId = res.data
						// 获取用户账号数据
						uni.request({
							url: 'http://localhost:8080/accounts',
							method: 'GET',
							data: {
								userId: res.data
							},
							success: (res) => {
								this.selectAccount = [];
								this.selectAccountOtherMsg = [];
								res.data.forEach((item, index, array) => {
									this.selectAccount.push(item.accountName);
									var tmp = {};
									tmp.accountId = item.accountId;
									tmp.money = item.accountFreeMoney;
									this.selectAccountOtherMsg.push(tmp);
								})
							}
						})
					}
				});
			},
			utcToBeijing(utc_datetime) { // utc转北京时间  例： 2020-05-08T04:25:44.000Z  -> 2020/05/08 12:25
						let T_pos = utc_datetime.indexOf("T");
						let Z_pos = utc_datetime.indexOf("Z");
						let year_month_day = utc_datetime.substr(0, T_pos);
						let hour_minute_second = utc_datetime.substr(T_pos + 1, Z_pos - T_pos - 1);
						let new_datetime = (year_month_day + " " + hour_minute_second).replace(/\.000/g, ""); // 去掉结尾.000  .replace(/\.|0+$/g, "")
						let a = new Date(Date.parse(new_datetime.replace(/\-/g, "/"))); // - ios不支持，所以替换为 / 
						let b = (a.getTime()) / 1000 + 8 * 60 * 60;
						let data = new Date(parseInt(b) * 1000);
						let beijing_datetime = this.addZero(data.getFullYear()) + "/" + (this.addZero(data.getMonth() + 1)) + "/" + this.addZero(data.getDate());
						return beijing_datetime;
			},
			addZero(node) { // 个位不足10补0
						return node < 10 ? "0" + node : node;
			},
		},
		created(){
			this.getAllAccount();
		}
	}
</script>

<style>
.remind{
	margin-top: 20rpx;
	margin-left: 20rpx;
}
.pickerView{
	margin-top: 10rpx;
}
input{
	margin-top: 20rpx;
}
button{
	width: 500rpx;
}
</style>
