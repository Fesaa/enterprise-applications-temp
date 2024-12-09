package art.ameliah.ehb.anki.api.services;

import art.ameliah.ehb.anki.api.services.model.IStringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class StringService implements IStringService {

    // https://github.com/Kareadita/Kavita/blob/c3a84734890668be33d1c0c110bac4e68f6666bc/API/Services/Tasks/Scanner/Parser/Parser.cs#L110
    private final Pattern normalizeRegex = Pattern.compile("[^\\p{L}0-9+!＊！＋]");

    @Override
    public String normalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return normalizeRegex.matcher(str).replaceAll("").trim().toLowerCase();
    }
}
