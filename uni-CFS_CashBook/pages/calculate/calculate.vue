<template>
	<view class="qiun-columns">
		<view style="margin-top: 100rpx;"></view>
		<scroll-view scroll-y="true" style="height: 1000rpx;" @scroll="scroll">
			<view class="qiun-charts">
				<!-- 注意，这里用里@tap来触发事件 -->
				<canvas canvas-id="canvasColumn" id="canvasColumn" class="charts" @tap="touchColumn"></canvas>
				<canvas canvas-id="canvasColumn1" id="canvasColumn" class="charts" @tap="touchColumn"></canvas>
			</view>
		</scroll-view>
		<tab-bar :current="5"></tab-bar>
	</view>
</template>

<script>
	import uCharts from '../../js_sdk/u-charts/u-charts/u-charts.js'
	import {
		isJSON
	} from '../../common/checker.js';
	var _self;
	var canvaColumn = null;
	export default {
		data() {
			return {
				cWidth: '',
				cHeight: '',
				pixelRatio: 1,
				textarea: [],
				scrollTop: 0,
				spendNames: [],
				accountNames: [],
				investMoneys: [],
				flowMoneys: [],
				spendMoneys: [],
				userId:'',
			}
		},
		created() {
			uni.getStorage({
				key: 'userId',
				success: (res) => {
					console.log(res);
					this.userId = res.data
					uni.request({
						url: 'http://localhost:8080/calculate',
						method:'GET',
						data: {
							userId:res.data
						},
						success:(res)=> {
							this.accountNames = res.data.accountNameItems;
							this.spendNames = res.data.spendNameItems;
							this.flowMoneys = res.data.flowMoneyItems;
							this.investMoneys = res.data.investMoneyItems;
							this.accountNames.forEach((item,index,arr)=>{
								if(typeof this.investMoneys[index] == "undefined"){
									this.investMoneys[index] = 0;
								}
							})
							this.getServerData();
						}
					})
				}
			})
			
		},
		onShareAppMessage() {
			//#ifdef MP-QQ
			qq.showShareMenu({
				showShareItems: ['qq', 'qzone', 'wechatFriends', 'wechatMoment']
			})
			//#endif
		},
		onLoad() {
			_self = this;
			//#ifdef MP-ALIPAY
			uni.getSystemInfo({
				success: function(res) {
					if (res.pixelRatio > 1) {
						//正常这里给2就行，如果pixelRatio=3性能会降低一点
						//_self.pixelRatio =res.pixelRatio;
						_self.pixelRatio = 2;
					}
				}
			});
			//#endif
			this.cWidth = uni.upx2px(750);
			this.cHeight = uni.upx2px(500);
		},
		methods: {
			getServerData() {
				let Column = {
					categories: [],
					series: []
				};

				
				Column.categories = this.accountNames;
				Column.series = [{
					area: [],
					color: "#8543e0",
					data: this.flowMoneys,
					index: 0,
					legendShape: "rect",
					name: "流动资金",
					pointShape: "circle",
					show: true,
					type: "column"
				}];
				_self.textarea[1] = JSON.stringify(Column);
				_self.showColumn("canvasColumn", Column); // 注册数据 流动资金数据

				Column.categories = this.accountNames;
				Column.series = [{
					area: [],
					color: "#f04864",
					data:  this.investMoneys,
					index: 0,
					legendShape: "rect",
					name: "投资资金",
					pointShape: "circle",
					show: true,
					type: "column"
				}];
				_self.textarea[2] = JSON.stringify(Column);
				_self.showColumn("canvasColumn1", Column); // 注册数据 流动资金数据

			},
			scroll: function(e) {
				this.scrollTop = e.detail.scrollTop
			},
			showColumn(canvasId, chartData) {
				canvaColumn = new uCharts({
					$this: _self,
					canvasId: canvasId,
					type: 'column',
					padding: [15, 5, 0, 15], //指表格的padding 上左下右边 盒子外的
					legend: { // 长度，盒子内的
						show: true,
						padding: 10, // 改变该参数会向上移动
						lineHeight: 11, // 行高，直方图的高度
						margin: 0,
					},
					fontSize: 11,
					background: '#FFFFFF',
					pixelRatio: _self.pixelRatio,
					animation: true,
					categories: chartData.categories,
					series: chartData.series,
					xAxis: {
						disableGrid: true,
					},
					yAxis: {
						data: [{
							position: 'right',
							axisLine: false,
							format: (val) => {
								return val.toFixed(0) + '元'
							},
						}]
					},
					dataLabel: true,
					width: _self.cWidth * _self.pixelRatio,
					height: _self.cHeight * _self.pixelRatio,
					extra: {
						column: {
							type: 'group',
							width: _self.cWidth * _self.pixelRatio * 0.45 / chartData.categories.length
						}
					}
				});

			},
			touchColumn(e) {
				//这里一定要加，将偏移量累加到该事件中
				//#ifndef H5
				e.changedTouches[0].pageY += this.scrollTop;
				e.mp.changedTouches[0].pageY += this.scrollTop;
				//#endif
				console.log(e);
				canvaColumn.showToolTip(e, {
					format: function(item, category) {
						if (typeof item.data === 'object') {
							return category + ' ' + item.name + ':' + item.data.value
						} else {
							return category + ' ' + item.name + ':' + item.data
						}
					}
				});
				canvaColumn.touchLegend(e, {
					animation: true
				});
			},
			changeData(textarea) {
				// _self.textarea[0] 将对应数据输入
				if (isJSON(textarea)) {
					let newdata = JSON.parse(textarea);
					canvaColumn.updateData({
						series: newdata.series,
						categories: newdata.categories,
						animation: true
					});
				} else {
					// uni.showToast({
					// 	title:'数据格式错误',
					// 	image:'../../../static/images/alert-warning.png'
					// })
				}
			}
		}
	}
</script>

<style>
	/*样式的width和height一定要与定义的cWidth和cHeight相对应*/
	.qiun-charts {
		margin-top: 60upx;
		width: 750upx;
		height: 480upx;
		background-color: #FFFFFF;
	}

	.charts {
		width: 750upx;
		height: 500upx;
		background-color: #FFFFFF;
	}
</style>
