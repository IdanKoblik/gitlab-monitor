package dev.idan.bgbot.listeners;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

//@Component
public class AutoCompleteListener extends ListenerAdapter {

/*    @Autowired
    ExternalTokenRepository externalTokenRepository;

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        if (event.getName().equals("fruit") && event.getFocusedOption().getName().equals("name")) {
            List<Command.Choice> options = Stream.of(words)
                    .filter(word -> word.startsWith(event.getFocusedOption().getValue())) // only display words that start with the user's current input
                    .map(word -> new Command.Choice(word, word)) // map the words to choices
                    .collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
    }

    private long[] getProjectIds() {
        List<>
    }*/
}
