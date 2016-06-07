package pl.com.bottega.photostock.sales.fun;

import org.junit.Assert;
import org.junit.Test;
import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.products.Picture;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by Admin on 04.06.2016.
 */
public class NonEmptyListTest {

    private FunList<String> l = FunList.create();
    Picture p1 = new Picture("name", "nr1", Money.FIVE_PL, true, new String[]{"tag1"});
    Picture p2 = new Picture("name", "nr2", Money.ONE_PL, false, new String[]{"tag2"});
    Picture p3 = new Picture("name", "nr3", Money.FIVE_PL, true, new String[]{"tag3"});

    @Test
    public void shouldAddElements() throws Exception {
        l = l.add("first").add("second").add("third").add("fifth");

        Assert.assertTrue(l.contains("first"));
        Assert.assertTrue(l.contains("fifth"));
    }

    @Test
    public void shouldReturnStringRepresentation() throws Exception {
        l = l.add("first").add("second").add("third").add("fifth");

        Assert.assertEquals("first, second, third, fifth", l.toString());
    }

    @Test
    public void shouldReturnSize() {
        l = l.add("first").add("second").add("third").add("fifth");
        Assert.assertEquals(4, l.size());
    }

    @Test
    public void shouldGetElement() {
        l = l.add("first").add("second").add("third").add("fifth");
        Assert.assertEquals("second", l.get(1));
    }

    @Test
    public void shouldFindElement() throws Exception {
        FunList<Integer> l = FunList.create();

        l = l.add(100).add(50).add(300).add(400);

        int number = l.find(new Predicate<Integer>() {
            @Override
            public boolean test(Integer i) {
                return i > 200;
            }
        });
        Assert.assertEquals(300, number);
        Assert.assertNull(l.find(new Predicate<Integer>() {
            @Override
            public boolean test(Integer i) {
                return i == 500;
            }
        }));
        int number2 = l.find(i -> i == 400);
        Assert.assertEquals(400, number2);
        Assert.assertEquals(50, (int) l.find(i -> i == 50));

        Predicate<Integer> p = (x) -> {
            System.out.println("Calling predicate");
            return x > 1000;
        };
        Assert.assertNull(l.find(p));
    }

    @Test
    public void shouldMapElements() {
        FunList<String> l = FunList.create();
        l = l.add("1").add("2").add("3").add("4").add("100");

        FunList<Integer> mapped = l.map(s -> Integer.valueOf(s));
        //FunList<Integer> mapped = l.map(Integer :: valueOf); method reference

        FunList<Integer> expected = FunList.create();
        expected = expected.add(1).add(2).add(3).add(4).add(100);

        FunList<Integer> listInt = mapped.map((s) -> Integer.valueOf(s));

        Assert.assertEquals(expected, listInt);
    }

    @Test
    public void shouldReduceElements() {
        FunList<Picture> l = FunList.create();
        l = l.add(p1).add(p2).add(p3);

        int availableCount = l.reduce(0, (accumulator, product) -> accumulator + (product.isAvailable() ? 1 : 0));

        Money total = l.reduce(Money.ZERO_PL, (accumulator, product) -> accumulator.add(product.calculatePrice()));

        FunList<String> tags = l.reduce(FunList.create(), (accumulator, product) -> {
            for (String tag : product.getTags())
                if (!accumulator.contains(tag))
                    accumulator = accumulator.add(tag);
            return accumulator;
        });

        Assert.assertEquals(2, availableCount);
        Assert.assertEquals(Money.FIVE_PL.add(Money.FIVE_PL).add(Money.ONE_PL), total);
        FunList<String> expectedTags = FunList.create();
        expectedTags = expectedTags.add("tag1").add("tag2").add("tag3");
        Assert.assertEquals(expectedTags, tags);
    }

    @Test
    public void shouldRemoveElement() {
        //given
        FunList<Picture> list = FunList.create();
        list = list.add(p1).add(p2).add(p3);
        //when
        list = list.remove(p3);
        //then
        FunList<Picture> expectedList = FunList.create();
        expectedList = expectedList.add(p1).add(p2);

        Assert.assertEquals(expectedList, list);
    }

    @Test
    public void shouldFilterList() {
        FunList<Integer> list = FunList.create();
        list = list.add(10).add(15).add(18).add(20);
        list = list.filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer i) {
                return i<20;
            }
        });
        FunList<Integer> expectedList = FunList.create();
        expectedList = expectedList.add(10).add(15).add(18);
        Assert.assertEquals(expectedList, list);
    }

    @Test
    public void shouldApplyMethodToEachElement() {
        Picture pic1 = new Picture("name", "nr11", Money.FIVE_PL, true, new String[]{"tag1"});
        Picture pic2 = new Picture("name", "nr21", Money.ONE_PL, false, new String[]{"tag2"});
        Picture pic3 = new Picture("name", "nr31", Money.FIVE_PL, true, new String[]{"tag3"});

        FunList<Picture> list= FunList.create();

        list = list.add(p1).add(p2).add(p3);

        list.each(s -> s.setNumber(s.getNumber()+"1"));

        FunList<Picture> expectedList = FunList.create();
        expectedList = expectedList.add(pic1).add(pic2).add(pic3);

        Assert.assertEquals(expectedList, list);
    }

    @Test
    public void shouldConcateLists() {
        l = l.add("one").add("two").add("three");
        FunList<String> other = FunList.create();
        other = other.add("four").add("five").add("six");

        FunList<String> expectedList = FunList.create();
        expectedList = expectedList.add("one").add("two").add("three").add("four").add("five").add("six");

        Assert.assertEquals(expectedList, l.concat(other));
    }

    @Test
    public void shouldGetSublist() {
        l = l.add("zero").add("one").add("two").add("three").add("four").add("five").add("six");
        l = l.sublist(0, 2);

        FunList<String> expectedList = FunList.create();
        expectedList = expectedList.add("zero").add("one").add("two");

        Assert.assertEquals(expectedList, l);
    }
}
