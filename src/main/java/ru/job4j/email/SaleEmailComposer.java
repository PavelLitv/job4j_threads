package ru.job4j.email;

public class SaleEmailComposer implements MessageComposer {
    @Override
    public Message compose(User user) {
        String subject = "We have a lot of new products.";
        String body = String.format("%s you haven't visited us for a long time, but we have a lot of new products and now there is a big sale.", user.userName());
        return new Message(subject, body, user.userName());
    }
}
