import App from './App';
import { mount,axios} from'enzyme';

describe('MessageList component', () => {
  let component;
  let messages;

  beforeEach(async () => {
    // Mount the component and fetch the messages
    component = mount(<App />);
    messages = await axios.get('http://2023sp-team-m.dokku.cse.lehigh.edu/messages');
  });

  it('should display all the messages returned from the API', () => {
    // Get the message elements from the DOM
    const messageElements = component.find('.message');
    
    // Check that the number of message elements matches the number of messages
    expect(messageElements.length).toEqual(messages.length);

    // Check that each message element contains the correct text
    messages.forEach((message, index) => {
      expect(messageElements.at(index).text()).toContain(message.mMessage);
      expect(messageElements.at(index).text()).toContain(message.mSubject);
      expect(messageElements.at(index).text()).toContain(message.mLikes);
    });
  });

  it('should allow users to add new messages', async () => {
    // Get the form and input elements from the DOM
    const form = component.find('form');
    const subjectInput = component.find('input[name="subject"]');
    const messageInput = component.find('textarea');

    // Fill out the form and submit it
    subjectInput.simulate('change', { target: { value: 'New message' } });
    messageInput.simulate('change', { target: { value: 'This is a new message.' } });
    form.simulate('submit');

    // Wait for the messages to be fetched again
    await component.instance().getMessages();

    // Check that the new message is displayed on the page
    const messageElements = component.find('.message');
    expect(messageElements.length).toEqual(messages.length + 1);
    expect(messageElements.at(messageElements.length - 1).text()).toContain('New message');
    expect(messageElements.at(messageElements.length - 1).text()).toContain('This is a new message.');
  });

  it('should allow users to delete messages', async () => {
    // Get the delete button from the first message element
    const deleteButton = component.find('.message').at(0).find('.delete-button');

    // Click the delete button
    deleteButton.simulate('click');

    // Wait for the messages to be fetched again
    await component.instance().getMessages();

    // Check that the first message is no longer displayed on the page
    const messageElements = component.find('.message');
    expect(messageElements.length).toEqual(messages.length - 1);
    expect(messageElements.at(0).text()).not.toContain(messages[0].mMessage);
  });

  it('should allow users to like messages', async () => {
    // Get the like button from the first message element
    const likeButton = component.find('.message').at(0).find('.like-button');

    // Get the initial number of likes for the first message
    const initialLikes = messages[0].mLikes;

    // Click the like button
    likeButton.simulate('click');

    // Wait for the messages to be fetched again
    await component.instance().getMessages();

    // Check that the number of likes for the first message has increased by one
    const messageElements = component.find('.message');
    expect(messageElements.at(0).text()).toContain(initialLikes + 1);
  });

  it('should allow users to dislike messages', async () => {
    // Get the like button from the first message element
    const likeButton = component.find('.message').at(0).find('.like-button');

    // Get the initial number of likes for the first message
    const initialLikes = messages[0].mLikes;

    // Click the like button
    likeButton.simulate('click');

    // Wait for the messages to be fetched again
    await component.instance().getMessages();

    // Check that the number of likes for the first message has increased by one
    const messageElements = component.find('.message');
    expect(messageElements.at(0).text()).toContain(initialLikes - 1);
  });


  
});