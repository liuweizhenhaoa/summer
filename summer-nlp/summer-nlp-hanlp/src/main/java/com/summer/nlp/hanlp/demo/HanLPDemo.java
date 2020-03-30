package com.summer.nlp.hanlp.demo;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.model.crf.CRFLexicalAnalyzer;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * 内存要求
 * 内存120MB以上（-Xms120m -Xmx120m -Xmn64m），标准数据包（35万核心词库+默认用户词典），分词测试正常。全部词典和模型都是惰性加载的，不使用的模型相当于不存在，可以自由删除。
 * HanLP对词典的数据结构进行了长期的优化，可以应对绝大多数场景。哪怕HanLP的词典上百兆也无需担心，因为在内存中被精心压缩过。如果内存非常有限，请使用小词典。HanLP默认使用大词典，同时提供小词典，请参考配置文件章节。
 * 写给正在编译HanLP的开发者
 * 如果你正在编译运行从Github检出的HanLP代码，并且没有下载data缓存，那么首次加载词典/模型会发生一个自动缓存的过程。
 * 自动缓存的目的是为了加速词典载入速度，在下次载入时，缓存的词典文件会带来毫秒级的加载速度。由于词典体积很大，自动缓存会耗费一些时间，请耐心等待。
 * 自动缓存缓存的不是明文词典，而是双数组Trie树、DAWG、AhoCorasickDoubleArrayTrie等数据结构。
 */
@Slf4j
public class HanLPDemo {


	public  void test1(){
		System.out.println(HanLP.segment("你好，欢迎使用HanLP汉语处理包！"));
	}

	// 标准分词
	public  void test2(){
		List<Term> termList = StandardTokenizer.segment("商品和服务");
		System.out.println(termList);
	}

	//NLP分词
	public  void test3(){
		System.out.println(NLPTokenizer.segment("我新造一个词叫幻想乡你能识别并标注正确词性吗？"));
		// 注意观察下面两个“希望”的词性、两个“晚霞”的词性
		System.out.println(NLPTokenizer.analyze("我的希望是希望张晚霞的背影被晚霞映红").translateLabels());
		System.out.println(NLPTokenizer.analyze("支援臺灣正體香港繁體：微软公司於1975年由比爾·蓋茲和保羅·艾倫創立。"));
	}

	public void  test4(){
//		索引分词
		List<Term> termList = IndexTokenizer.segment("主副食品");
		for (Term term : termList)
		{
			System.out.println(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
		}
//		说明
//		索引分词IndexTokenizer是面向搜索引擎的分词器，能够对长词全切分，另外通过term.offset可以获取单词在文本中的偏移量。
//		任何分词器都可以通过基类Segment的enableIndexMode方法激活索引模式。
	}

	public  void  test5(){
//		5. N-最短路径分词
		Segment nShortSegment = new NShortSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
		Segment shortestSegment = new DijkstraSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
		String[] testCase = new String[]{
				"今天，刘志军案的关键人物,山西女商人丁书苗在市二中院出庭受审。",
				"刘喜杰石国祥会见吴亚琴先进事迹报告团成员",
		};
		for (String sentence : testCase)
		{
			System.out.println("N-最短分词：" + nShortSegment.seg(sentence) + "\n最短路分词：" + shortestSegment.seg(sentence));
		}
//		说明
//		N最短路分词器NShortSegment比最短路分词器慢，但是效果稍微好一些，对命名实体识别能力更强。
//		一般场景下最短路分词的精度已经足够，而且速度比N最短路分词器快几倍，请酌情选择。
//		算法详解
//《N最短路径的Java实现与分词应用》
	}

	public  void test6() throws IOException {
//		6. CRF分词
// 	说明
//		CRF对新词有很好的识别能力，但是开销较大。
//		算法详解
//《CRF中文分词、词性标注与命名实体识别》
		CRFLexicalAnalyzer analyzer = new CRFLexicalAnalyzer();
		String[] tests = new String[]{
				"商品和服务",
				"上海华安工业（集团）公司董事长谭旭光和秘书胡花蕊来到美国纽约现代艺术博物馆参观",
				"微软公司於1975年由比爾·蓋茲和保羅·艾倫創立，18年啟動以智慧雲端、前端為導向的大改組。" // 支持繁体中文
		};
		for (String sentence : tests)
		{
			System.out.println(analyzer.analyze(sentence));
		}
	}

	public  void  test7(){
//		7. 极速词典分词
/**
 * 演示极速分词，基于AhoCorasickDoubleArrayTrie实现的词典分词，适用于“高吞吐量”“精度一般”的场合
 * //		说明
 * //		极速分词是词典最长分词，速度极其快，精度一般。
 * //		在i7-6700K上跑出了4500万字每秒的速度。
 * //		算法详解
 * //《Aho Corasick自动机结合DoubleArrayTrie极速多模式匹配》
 */
		String text = "江西鄱阳湖干枯，中国最大淡水湖变成大草原";
		System.out.println(SpeedTokenizer.segment(text));
		long start = System.currentTimeMillis();
		int pressure = 1000000;
		for (int i = 0; i < pressure; ++i)
		{
			SpeedTokenizer.segment(text);
		}
		double costTime = (System.currentTimeMillis() - start) / (double)1000;
		System.out.printf("分词速度：%.2f字每秒", text.length() * pressure / costTime);

	}


	public static void main(String[] args) throws IOException {
		HanLPDemo hanLPDemo = new HanLPDemo();
//		hanLPDemo.test1();;
//		hanLPDemo.test2();;
//		hanLPDemo.test3();;
//		hanLPDemo.test4();;
//
//		hanLPDemo.test5();;






















			}

}

