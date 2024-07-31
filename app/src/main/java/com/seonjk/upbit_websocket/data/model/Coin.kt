package com.seonjk.upbit_websocket.data.model

/**
 * 현재가 (Ticker) 데이터
 * */
data class Coin(
    /** 타입 */
    val type: String,
    /** 마켓 코드 (ex. KRW-BTC) */
    val code: String,
    /** 시가 */
    val opening_price: Double,
    /** 고가 */
    val high_price: Double,
    /** 저가 */
    val low_price: Double,
    /** 현재가 */
    val trade_price: Double,
    /** 전일 종가 */
    val prev_closing_price: Double,
    /** 누적 거래대금(UTC 0시 기준) */
    val acc_trade_price: Double,
    /**
     * 전일 대비
     * RISE : 상승
     * EVEN : 보합
     * FALL : 하락
     * */
    val change: String,
    /** 부호 없는 전일 대비 값 */
    val change_price: Double,
    /** 전일 대비 값 */
    val signed_change_price: Double,
    /** 부호 없는 전일 대비 등락율 */
    val change_rate: Double,
    /** 전일 대비 등락율 */
    val signed_change_rate: Double,
    /** 매수/매도 구분 */
    val ask_bid: String,
    /** 가장 최근 거래량 */
    val trade_volume: Double,
    /** 최근 거래 일자(UTC) yyyyMMdd */
    val trade_date: String,
    /** 누적 거래량(UTC 0시 기준) */
    val acc_trade_volume: Double,
    /** 최근 거래 시각(UTC) HHmmss */
    val trade_time: String,
    /** 체결 타임스탬프 (milliseconds) */
    val trade_timestamp: Long,
    /** 누적 매도량 */
    val acc_ask_volume: Double,
    /** 누적 매수량 */
    val acc_bid_volume: Double,
    /** 52주 최고가 */
    val highest_52_week_price: Double,
    /** 52주 최고가 달성일 yyyy-MM-dd */
    val highest_52_week_date: String,
    /** 52주 최저가 */
    val lowest_52_week_price: Double,
    /** 52주 최저가 달성일 yyyy-MM-dd */
    val lowest_52_week_date: String,
    /**
     * 거래상태
     * PREVIEW : 입금지원
     * ACTIVE : 거래지원가능
     * DELISTED : 거래지원종료
     * */
    val market_state: String,
    /** 거래 정지 여부 */
    val is_trading_suspended: Boolean,
    /** 상장폐지일 */
    val delisting_date: String?,
    /**
     * 유의 종목 여부
     * NONE : 해당없음
     * CAUTION : 투자유의
     * */
    val market_warning: String,
    /** 타임스탬프 (millisecond) */
    val timestamp: Long,
    /** 24시간 누적 거래대금 */
    val acc_trade_price_24h: Double,
    /** 24시간 누적 거래량 */
    val acc_trade_volume_24h: Double,
    /**
     * 스트림 타입
     * SNAPSHOT : 스냅샷
     * REALTIME : 실시간
     * */
    val stream_type: String
)
