<template>
	<!-- #ifdef APP-NVUE -->
	<cell>
	<!-- #endif -->
	<view :class="disabled ? 'uni-list-item--disabled' : ''" :hover-class="disabled || showSwitch ? '' : 'uni-list-item--hover'"
	 class="uni-list-item" @click="onClick">
		<view class="uni-list-item__container" :class="{'uni-list-item--first':isFirstChild}">
			<view v-if="thumb" class="uni-list-item__icon">
				<image :src="thumb" class="uni-list-item__icon-img" />
			</view>
			<view v-else-if="showExtraIcon" class="uni-list-item__icon">
				<uni-icons :color="extraIcon.color" :size="extraIcon.size" :type="extraIcon.type" class="uni-icon-wrapper" />
			</view>
			<view class="uni-list-item__content">
				<slot />
				<text class="uni-list-item__content-title">{{ title }}</text>
				<text v-if="note" class="uni-list-item__content-note">{{ note }}</text>
			
			</view>
			<view><text v-if="rightWord" class="uni-list-item__content-right" :style="{ color: rightWordColor}">{{ rightWord }}</text></view>
			<view v-if="showBadge || showArrow || showSwitch" class="uni-list-item__extra">
				<uni-badge v-if="showBadge" :type="badgeType" :text="badgeText" />
				<switch v-if="showSwitch" :disabled="disabled" :checked="switchChecked" @change="onSwitchChange" />
				<uni-icons v-if="showArrow" :size="20" class="uni-icon-wrapper" color="#bbb" type="arrowright" />
			</view>
		</view>
	</view>
	<!-- #ifdef APP-NVUE -->
	</cell>
	<!-- #endif -->
</template>

<script>
	import uniIcons from '../uni-icons/uni-icons.vue'
	import uniBadge from '../uni-badge/uni-badge.vue'
	export default {
		name: 'QimingListItem',
		components: {
			uniIcons,
			uniBadge
		},
		props: {
			rightWord:{
				type: String,
				default: ''
			},//右侧文字
			rightWordColor: {
				type: String,
				default: ''
			},
			title: {
				type: String,
				default: ''
			}, // 列表标题
			note: {
				type: String,
				default: ''
			}, // 列表描述
			disabled: {
				// 是否禁用
				type: [Boolean, String],
				default: false
			},
			showArrow: {
				// 是否显示箭头
				type: [Boolean, String],
				default: true
			},
			showBadge: {
				// 是否显示数字角标
				type: [Boolean, String],
				default: false
			},
			showSwitch: {
				// 是否显示Switch
				type: [Boolean, String],
				default: false
			},
			switchChecked: {
				// Switch是否被选中
				type: [Boolean, String],
				default: false
			},
			badgeText: {
				// badge内容
				type: String,
				default: ''
			},
			badgeType: {
				// badge类型
				type: String,
				default: 'success'
			},
			thumb: {
				// 缩略图
				type: String,
				default: ''
			},
			showExtraIcon: {
				// 是否显示扩展图标
				type: [Boolean, String],
				default: false
			},
			extraIcon: {
				type: Object,
				default () {
					return {
						type: 'contact',
						color: '#000000',
						size: 20
					}
				}
			}
		},
		inject: ['list'],
		data() {
			return {
				isFirstChild: false
			}
		},
		mounted() {
			if (!this.list.firstChildAppend) {
				this.list.firstChildAppend = true
				this.isFirstChild = true
			}
		},
		methods: {
			onClick() {
				this.$emit('click')
			},
			onSwitchChange(e) {
				this.$emit('switchChange', e.detail)
			}
		}
	}
</script>

<style lang="scss" scoped>
	@import '~@/uni.scss';

	$list-item-pd: $uni-spacing-col-lg $uni-spacing-row-lg;

	.uni-list-item {
		font-size: $uni-font-size-lg;
		position: relative;
		flex-direction: column;
		justify-content: space-between;
		padding-left: $uni-spacing-row-lg;
	}

	.uni-list-item--disabled {
		opacity: 0.3;
	}

	.uni-list-item--hover {
		background-color: $uni-bg-color-hover;
	}

	.uni-list-item__container {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		padding: $list-item-pd;
		padding-left: 0;
		flex: 1;
		position: relative;
		justify-content: space-between;
		align-items: center;
		border-top-color: $uni-border-color;
		border-top-style: solid;
		border-top-width: 1px;
	}

	.uni-list-item--first {
		border-top-width: 0px;
	}

	.uni-list-item__content {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex: 1;
		overflow: hidden;
		flex-direction: column;
		color: #3b4144;

	}

	.uni-list-item__content-title {
		font-size: $uni-font-size-base;
		color: #3b4144;
		overflow: hidden;
	}

	.uni-list-item__content-note {
		margin-top: 6rpx;
		color: $uni-text-color-grey;
		font-size: $uni-font-size-sm;
		overflow: hidden;
	}

	.uni-list-item__content-note {
		margin-top: 6rpx;
		right: 0;
		color: $uni-text-color-grey;
		font-size: $uni-font-size-sm;
		overflow: hidden;
	}
	

	.uni-list-item__extra {
		// width: 25%;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		justify-content: flex-end;
		align-items: center;
	}

	.uni-list-item__icon {
		margin-right: 18rpx;
		flex-direction: row;
		justify-content: center;
		align-items: center;
	}

	// .uni-list-item__icon-img {
	// 	height: $uni-img-size-base;
	// 	width: $uni-img-size-base;
	// }
	/* 此处修改了图片大小，如需复原，松开上面注释，将以下代码解除即可*/
	.uni-list-item__icon-img {
		height: $home-list-img;
		width: $home-list-img;
	}
	/*此处添加了右侧文字样式*/
	.uni-list-item__content-right {
		font-size: 20px;
		color: #333333;
		letter-spacing: 0.55px;
		text-align: right;
	}
</style>
