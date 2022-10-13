package com.softserve.teachua.service.test;

import com.softserve.teachua.dto.test.question.QuestionProfile;
import com.softserve.teachua.dto.test.test.*;
import com.softserve.teachua.exception.NotExistException;
import com.softserve.teachua.model.User;
import com.softserve.teachua.model.test.*;
import com.softserve.teachua.repository.test.SubscriptionRepository;
import com.softserve.teachua.repository.test.TestRepository;
import com.softserve.teachua.service.UserService;
import com.softserve.teachua.service.test.impl.TestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestServiceTest {
    @Mock
    TestRepository testRepository;
    @Mock
    SubscriptionRepository subscriptionRepository;
    @Mock
    GroupService groupService;
    @Mock
    TopicService topicService;
    @Mock
    ModelMapper modelMapper;
    @Mock
    UserService userService;
    @Mock
    QuestionService questionService;
    @Mock
    QuestionTestService questionTestService;
    @Mock
    QuestionCategoryService questionCategoryService;
    @Mock
    QuestionTypeService questionTypeService;

    @InjectMocks
    TestServiceImpl testService;

    private final Long EXISTING_TEST_ID = 1L;
    private final Long NOT_EXISTING_TEST_ID = 100L;

    private final Long USER_ID = 1L;

    private com.softserve.teachua.model.test.Test test;
    private PassTest passTest;
    private Question question;
    private QuestionProfile questionProfile;
    private TestProfile testProfile;
    private ViewTest viewTest;
    private SuccessCreatedTest successCreatedTest;
    private CreateTest createTest;
    private List<Subscription> subscriptionsByUser;
    private List<Group> groupsByTest;

    @BeforeEach
    void setUp() {
        ModelMapper mapper = new ModelMapper();
        subscriptionsByUser = new ArrayList<>();
        groupsByTest = new ArrayList<>();
        questionProfile = generateQuestion();
        Subscription firstSubscription = Subscription.builder()
                .expirationDate(LocalDate.of(2022, 1, 31))
                .build();
        firstSubscription.setGroup(Group.builder()
                .id(1L)
                .endDate(LocalDate.of(2022, 1, 31))
                .build());
        Subscription secondSubscription = Subscription.builder()
                .expirationDate(LocalDate.of(2022, 1, 31))
                .build();
        secondSubscription.setGroup(Group.builder()
                .id(3L)
                .endDate(LocalDate.of(2022, 1, 31))
                .build());
        subscriptionsByUser.add(firstSubscription);
        subscriptionsByUser.add(secondSubscription);
        groupsByTest.add(Group.builder()
                .id(1L)
                .endDate(LocalDate.of(2022, 1, 31))
                .build());
        groupsByTest.add(Group.builder()
                .id(2L)
                .endDate(LocalDate.of(2022, 1, 31))
                .build());

        createTest = CreateTest.builder()
                    .difficulty(5)
                    .duration(23)
                    .title("testTitle")
                    .topicTitle("cats")
                    .questions(new ArrayList<>())
                    .description("testDescription")
                    .build();
        passTest = PassTest.builder()
                    .title("passTest")
                    .build();

        test = mapper.map(createTest, com.softserve.teachua.model.test.Test.class);
        testProfile = mapper.map(createTest, TestProfile.class);
        viewTest = mapper.map(createTest, ViewTest.class);
        successCreatedTest = mapper.map(createTest, SuccessCreatedTest.class);
        question = mapper.map(questionProfile, Question.class);

        Stream.generate(this::generateQuestion)
                .limit(5)
                .forEach(question -> createTest.addQuestion(question));
    }

    @Test
    void findByExistingIdShouldReturnTest() {
        when(testRepository.findById(EXISTING_TEST_ID)).thenReturn(Optional.of(test));
        assertEquals(test, testService.findById(EXISTING_TEST_ID));
    }

    @Test
    void findByNullShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> testService.findById(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findByNotExistingIdShouldThrowNotExistException() {
        when(testRepository.findById(NOT_EXISTING_TEST_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> testService.findById(NOT_EXISTING_TEST_ID))
                .isInstanceOf(NotExistException.class);
    }

    @Test
    void findAllActiveTestsShouldReturnListOfTests() {
        test.setActive(true);
        when(testRepository.findActiveTests()).thenReturn(Collections.singletonList(test));
        assertEquals(test, testService.findActiveTests().get(0));
    }

    @Test
    void findAllArchivedTestsShouldReturnListOfTests() {
        test.setArchived(true);
        when(testRepository.findActiveTests()).thenReturn(Collections.singletonList(test));
        assertEquals(test, testService.findActiveTests().get(0));
    }

    @Test
    void findAllUnarchivedTestsShouldReturnListOfTests() {
        test.setArchived(false);
        when(testRepository.findActiveTests()).thenReturn(Collections.singletonList(test));
        assertEquals(test, testService.findActiveTests().get(0));
    }

    @Test
    void findAllTestsByExistingGroupIdShouldReturnListOfTests() {
        final Long EXISTING_GROUP_ID = 1L;
        when(testRepository.findAllByGroupId(EXISTING_GROUP_ID)).thenReturn(Collections.singletonList(test));
        assertEquals(test, testService.findAllByGroupId(EXISTING_GROUP_ID).get(0));
    }

    @Test
    void findAllTestsByNotExistingGroupIdShouldReturnListOfTests() {
        final Long NOT_EXISTING_GROUP_ID = 100L;
        when(testRepository.findAllByGroupId(NOT_EXISTING_GROUP_ID)).thenReturn(Collections.emptyList());
        assertEquals(0, testService.findAllByGroupId(NOT_EXISTING_GROUP_ID).size());
    }

    @Test
    void findAllTestsByNullShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> testService.findAllByGroupId(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findPassTestByExistingIdShouldReturnPassTest() {
        when(testRepository.findById(EXISTING_TEST_ID)).thenReturn(Optional.of(test));
        when(modelMapper.map(test, PassTest.class)).thenReturn(passTest);
        when(questionService.findQuestionsByTest(test)).thenReturn(Collections.emptyList());
        PassTest actual = testService.findPassTestById(EXISTING_TEST_ID);
        assertEquals(passTest, actual);
    }

    @Test
    void findAllArchivedTestProfilesShouldReturnListOfTestProfiles() {
        test.setArchived(true);
        when(testRepository.findActiveTests()).thenReturn(Collections.singletonList(test));
        when(modelMapper.map(test, TestProfile.class)).thenReturn(testProfile);
        assertEquals(testProfile, testService.findArchivedTestProfiles().get(0));
    }

    @Test
    void findAllUnarchivedTestProfilesShouldReturnListOfTestProfiles() {
        test.setArchived(false);
        when(testRepository.findActiveTests()).thenReturn(Collections.singletonList(test));
        when(modelMapper.map(test, TestProfile.class)).thenReturn(testProfile);
        assertEquals(testProfile, testService.findArchivedTestProfiles().get(0));
    }

    @Test
    void findViewTestByExistingIdShouldReturnViewTest() {
        when(testRepository.findById(EXISTING_TEST_ID)).thenReturn(Optional.of(test));
        when(modelMapper.map(test, ViewTest.class)).thenReturn(viewTest);
        when(userService.getCurrentUser()).thenReturn(new User());
        assertEquals(viewTest, testService.findViewTestById(EXISTING_TEST_ID));
    }

    @Test
    void hasActiveSubscriptionByUserIdAndTestIdShouldReturnTrue() {
        when(subscriptionRepository.findAllByUserId(USER_ID)).thenReturn(subscriptionsByUser);
        when(groupService.findAllByTestId(EXISTING_TEST_ID)).thenReturn(groupsByTest);
        assertTrue(testService.hasActiveSubscription(USER_ID, EXISTING_TEST_ID));
    }

    @Test
    void hasActiveSubscriptionByUserIdAndTestIdShouldReturnFalse() {
        subscriptionsByUser.clear();
        when(subscriptionRepository.findAllByUserId(USER_ID)).thenReturn(subscriptionsByUser);
        when(groupService.findAllByTestId(EXISTING_TEST_ID)).thenReturn(groupsByTest);
        assertFalse(testService.hasActiveSubscription(USER_ID, EXISTING_TEST_ID));
    }

    @Test
    void hasActiveSubscriptionByNullShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> testService.hasActiveSubscription(null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void archiveTestByExistingTestIdShouldReturnTestProfileWithArchivedIsTrue() {
        testProfile.setArchived(true);
        when(testRepository.findById(EXISTING_TEST_ID)).thenReturn(Optional.of(test));
        when(modelMapper.map(test, TestProfile.class)).thenReturn(testProfile);
        assertTrue(testService.archiveTestById(EXISTING_TEST_ID).isArchived());
    }

    @Test
    void restoreTestByExistingTestIdShouldReturnTestProfileWithArchivedIsFalse() {
        when(testRepository.findById(EXISTING_TEST_ID)).thenReturn(Optional.of(test));
        when(modelMapper.map(test, TestProfile.class)).thenReturn(testProfile);
        assertFalse(testService.restoreTestById(EXISTING_TEST_ID).isArchived());
    }

    @Test
    void createValidTestShouldReturnSuccessCreatedTest() {
        String topicTitle = createTest.getTopicTitle();
        String categoryTitle = questionProfile.getCategoryTitle();
        Topic topic = Topic.builder()
                .title(topicTitle)
                .build();
        when(topicService.findByTitle(topicTitle)).thenReturn(topic);
        when(questionCategoryService.findByTitle(categoryTitle)).thenReturn(QuestionCategory.builder()
                .title(categoryTitle)
                .build());
        when(questionTestService.save(any())).thenReturn(any());
        when(questionService.save(question)).thenReturn(question);
        when(questionTypeService.findByTitle("checkbox")).thenReturn(QuestionType.builder()
                .title("checkbox")
                .build());
        when(modelMapper.map(createTest, com.softserve.teachua.model.test.Test.class)).thenReturn(test);
        when(modelMapper.map(createTest, SuccessCreatedTest.class)).thenReturn(successCreatedTest);
        when(modelMapper.map(questionProfile, Question.class)).thenReturn(question);
        assertEquals(successCreatedTest, testService.addTest(createTest));
    }

    @Test
    void createTestWithNullShouldReturnSuccessCreatedTest() {
        assertThatThrownBy(() -> testService.addTest(null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private QuestionProfile generateQuestion() {
        return QuestionProfile.builder()
                .answerTitles(Arrays.asList("first", "second", "third", "fourth"))
                .categoryTitle("questionCategory")
                .description("questionDescription")
                .title("questionTitle")
                .value(5)
                .correctAnswerIndexes(Arrays.asList(1, 2))
                .build();
    }
}
